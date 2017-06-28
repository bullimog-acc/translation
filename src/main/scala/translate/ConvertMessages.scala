package translate

/*
 * Copyright 2015-2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

object ConvertMessages extends ConvertMessages with App {}

class ConvertMessages extends {

//  def main(args: Array[String]): Unit = {


    //compares messages.en with existingTranslations.csv, to create a new, marked-up out.csv
    val message2csv =  new Message2Csv("out.csv")
    message2csv.messages2csv()

    //creates _messages.cy, from Translations.csv
    Csv2Message.csv2Messages("Translations.csv")

//  }
}


/*
 a. Messages.en is the definitive hand-crafted list of messages in the project, so never auto generated.
 b. csv file is generated from messages.en, plus matched cy content, from tracked csv file (when available).
 c. Is cy line is found in csv?, compare line in csv with line in messages.en. Outcome: [New message / Message changed / Already translated]
 d. Messages.cy is always a straight generation from a csv file. New/Untranslated En messages are ignored (for now).


 1. Create application, using Play i18n, with text in Messages file(s)
 2. Run conversion, to create a csv file, from the Messages file(s)
 3. Track csv in Git and send file to translators.
 4. Receive csv back, with translations populated.
 5. Check this new csv into Git, superseeding the one in step 3.
 6. Extract the Welsh translations from the file, using this tool.
 7. Run the Tool against the messages.en. If the newly created csv highlights any changes,
     then the csv will need to be sent back to translators. The lines which have changed will be marked in the file.
 8. Send screenshots to translation team, for confirmation.
 */



