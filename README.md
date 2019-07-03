# translation

This tool is intended to make translation of English Play Framework messages files to other languages (specifically Welsh), simpler.
When first used with an untranslated project, it can extract English messages (key/value pairs) into a csv file, for translation.

Once the csv file has been populated with translations, the tool can extract the Welsh key/value pairs into a messages.cy file.
The tool may then be used to cross-reference the latest English messages file against a translated csv file, to ensure synchronisation. It will apply comments against each line, to indicate:
 - a new, untranslated English message
 - an existing, changed English message
 - an existing, unchanged English message, which already has a Welsh translation

# Executing

> sbt run
Input and output files can then be specified using the UI.


# Process
 1. Create application, using Play i18n, with text in Messages file(s)
 2. Run "(git) Messages to Csv", to create a csv file from the English Messages file.
 3. Provide csv to a translator, to make the translations.
 4. Receive csv back, with translations populated.
 5. Extract the Welsh translations from the file, using the "Csv to Messages" tool.
 6. At a later date, run the "(git) Messages to Csv" Tool against the repository, specifying the commit ref, from when the translations were last added to the repository. If the newly created csv highlights any changes, this indicates that additional translations are required. I.e. the English file has changed since the commit ref specified.
 
 
# Notes:
 1. Messages.en is the definitive hand-crafted list of messages in the project, so never auto generated.
 2. csv file is generated from messages.en, plus matched cy content (when available).
 3. Messages.cy is always a straight generation from a csv file. New/Untranslated English messages are ignored.
