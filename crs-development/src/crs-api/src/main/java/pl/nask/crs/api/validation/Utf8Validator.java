package pl.nask.crs.api.validation;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.commons.utils.Validator;

/**
 * Helper validator and normalizer for utf8.
 */
public final class Utf8Validator {

    private static final Utf8Validator instance = new Utf8Validator();

    private Utf8Validator() {}

    public static <T> T validated(T in) throws IncorrectUtf8FormatException {
        return instance.doValidate(in);
    }

    /**
     * Cache of fields inside classes, so that I don't have to collect fields every time I validate
     * object of the same type
     */
    private final ConcurrentHashMap<Class<?>, List<Field>> fieldsCache = new ConcurrentHashMap<>();

    /**
     * Classes that should be just returned.
     */
    private static List<Class<?>> copyAsIsClasses = Arrays.asList(new Class<?>[] {Integer.class, Long.class,
            Float.class, Double.class, Boolean.class, Date.class, BigDecimal.class});
    /**
     * Map of precise validators, that is validators that work only on instances of class from key, not on its
     * subclasses.
     */
    private static Map<Class<?>, ConcreteValidator<?>> concreteValidators = new HashMap<>();

    /**
     * Validators that work on any subclass of class from key, essential for collections, since
     * we want e.g. one List handler for any subclass of List. Keys should be separate classes, with
     * first common ancestor being Object.class. Otherwise there is no guarantee which validator
     * would be picked up for an object that suits many keys.
     */
    private static Map<Class<?>, ConcreteValidator<?>> baseConcreteValidators = new HashMap<>();

    static private <T> void registerConcreteValidator(Class<T> cls, ConcreteValidator<T> validator) {
        concreteValidators.put(cls, validator);
    }

    static private <T> void registerBaseConcreteValidator(Class<T> cls, ConcreteValidator<T> validator) {
        baseConcreteValidators.put(cls, validator);
    }

    static private <T> ConcreteValidator<T> getFastValidator(Class<? extends T> cls) {
        if (concreteValidators.containsKey(cls)) {
            return (ConcreteValidator<T>) concreteValidators.get(cls);
        }
        // ok, no specific validator, let's check base ones;
        for (Class<?> baseCls : baseConcreteValidators.keySet()) {
            if (baseCls.isAssignableFrom(cls)) {
                return (ConcreteValidator<T>) baseConcreteValidators.get(baseCls);
            }
        }
        return null;
    }

    static {
        registerConcreteValidator(String.class, new ConcreteValidator<String>() {
            @Override
            public String validate(String in) throws IncorrectUtf8FormatException {
                return Validator.getNormalizedAndValidatedUtf8(in);
            }
        });
        registerConcreteValidator(AuthenticatedUserVO.class, new ConcreteValidator<AuthenticatedUserVO>() {
            @Override
            public AuthenticatedUserVO validate(AuthenticatedUserVO in) throws IncorrectUtf8FormatException {
                final AuthenticatedUserVO result = new AuthenticatedUserVO();
                result.setUsername(Validator.getNormalizedAndValidatedUtf8(in.getUsername()));
                result.setAuthenticationToken(Validator.getNormalizedAndValidatedUtf8(in.getAuthenticationToken()));
                result.setPasswordChangeRequired(in.isPasswordChangeRequired());
                return result;
            }
        });
        registerBaseConcreteValidator(List.class, new ConcreteValidator<List>() {
            @Override
            public List validate(List inList) throws IncorrectUtf8FormatException {
                List result = new ArrayList();
                for (Object o : inList) {
                    result.add(validated(o));
                }
                return result;
            }
        });
        registerBaseConcreteValidator(Set.class, new ConcreteValidator<Set>() {
            @Override
            public Set validate(Set in) throws IncorrectUtf8FormatException {
                Set result = new HashSet();
                for (Object o : in) {
                    result.add(validated(o));
                }
                return result;
            }
        });
    }

    /**
     * <p>Performs deep copy of argument with automatic utf-8 normalization and validation
     * of <code>String</code> fields. Returns new object with all <code>String</code>
     * instances normalized or fails with throwning {@link IncorrectUtf8FormatException}.</p>
     *
     * <p>This method has strong assumptions about topology of fields inside <code>T</code>.
     * T and all it's fields should be simple objects with no final fields and conforming
     * with bean requirements (public non-argument constructor, getters and setters for
     * fields) or lists of those. It's also assumed that linking inside object (pointing twice
     * or more at the same object instance) is not a problem and as such is not dealt with.</p>
     *
     * @param in object to normalize
     * @param <T> type of argument
     * @return new instance of object of class T with all <code>String</code>s normalized
     * @throws pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException if object has utf-8
     * <code>String</code> with 4-byte characters.
     */
    private <T> T doValidate(final T in) throws IncorrectUtf8FormatException {
        if (in == null)
            return null;

        if (in.getClass().isPrimitive())
            return in;

        for (Class<?> cls : copyAsIsClasses)
            if (cls.isAssignableFrom(in.getClass()))
                return in;

        if (in instanceof Enum<?>)
            return in;

        Class<? extends T> cls = (Class<? extends T>) in.getClass();
        ConcreteValidator<T> validator = getFastValidator(cls);
        if (validator != null) {
            return validator.validate(in);
        }
        if (cls.isArray()) {
            return doValidateArray(in);
        }

        try {
            T result = cls.newInstance();

            for (Field f : allFields(cls)) {
                final Object in1 = f.get(in);
                f.set(result, doValidate(in1));
            }
            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Cannot validate object of class " + in.getClass().getCanonicalName()
                    + " for UTF-8");
        }
    }

    private <T> T doValidateArray(T in) throws IncorrectUtf8FormatException {
        final int length = Array.getLength(in);
        final T newAry = (T) Array.newInstance(in.getClass().getComponentType(), length);
        for (int i = 0; i < length; i++) {
            Array.set(newAry, i, doValidate(Array.get(in, i)));
        }
        return newAry;
    }

    private void addAll(final List<Field> l, final Field[] fields) {
        for (final Field field : fields) {
            final int modifiers = field.getModifiers();
            // I know I'm not interested in static fields
            if (!Modifier.isStatic(modifiers)) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                l.add(field);
            }
        }
    }

    private List<Field> allFields(final Class<?> c) {
        List<Field> l = fieldsCache.get(c);
        if (l == null) {
            l = new LinkedList<>();
            final Field[] fields = c.getDeclaredFields();
            addAll(l, fields);
            Class<?> sc = c;
            while ((sc = sc.getSuperclass()) != Object.class && sc != null) {
                addAll(l, sc.getDeclaredFields());
            }
            fieldsCache.putIfAbsent(c, l);
        }
        return l;
    }

    private interface ConcreteValidator<T> {
        T validate(T in) throws IncorrectUtf8FormatException;
    }
}
