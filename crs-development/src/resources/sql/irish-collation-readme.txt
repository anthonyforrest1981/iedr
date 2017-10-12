Instructions on adding Irish collation:

1. Find out where collations are stored:

   mysql> show variables like 'character_sets_dir';

2. Open Index.xml from this directory and add the following within the utf8 charset tag:

    <collation name="utf8_irish_accent_ci" id="1122" version="5.2.0">
        <rules>
            <reset>A</reset>
            <p>\u00c1</p>
            <t>\u00e1</t>
            <reset>E</reset>
            <p>\u00c9</p>
            <t>\u00e9</t>
            <reset>I</reset>
            <p>\u00cd</p>
            <t>\u00ed</t>
            <reset>O</reset>
            <p>\u00d3</p>
            <t>\u00f3</t>
            <reset>U</reset>
            <p>\u00da</p>
            <t>\u00fa</t>
        </rules>
    </collation>

3. Restart mysql server and make sure the collation is present:

   mysql> show collation like 'utf8_irish_accent_ci';

Detailed instructions can be found here: https://dev.mysql.com/doc/refman/5.6/en/ldml-collation-example.html
