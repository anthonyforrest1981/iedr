# What is it

In order to solve CRS-1337 we have to provide custom-built version of displaytag
library.

# Installing

script install_displaytag.sh (after local changes to accomodate different users!)
will install the library into a local maven repository, so that maven can find
it and build the project.

# settings.xml

In order for maven to actually use different local repository you need to
either provide or edit your .m2/settings.xml maven settings file so that
it points at your local repository.

# Installing into .m2

You can remove --localRepositoryPath from install_displaytag.sh - it will
cause displaytag to be installed into default .m2 folder. This has little
downsides, but when maven fails it's common to rm -rf .m2/repository in order
to clean maven packages. If displaytag is installed into .m2 it will have to
be reinstalled every time you purge .m2/repository.
