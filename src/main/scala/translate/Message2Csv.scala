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

import util.{CsvReader, FileReader, KeyValueParser, WrappedPrintWriter}


class Message2Csv(csvFileName: String) extends KeyValueParser with FileReader with CsvReader{

  type translationLine = (String,(String, String))
  type messageLine = (String, String)

  lazy val csvFile = new WrappedPrintWriter(csvFileName)

  def messages2csv():Unit = {

    val enMap = fetchMessages("messages.en")
    val existingTranslations = readFromCsv("existingTranslations.csv")

    csvFile.println("Key\tEnglish\tWelsh\tComments")
    enMap.map{ enMessage =>

      val oExistingTranslation = existingTranslations.find(translation => enMessage._1 == translation._1)

      val output = oExistingTranslation.fold(enMessage._1 + "\t" + enMessage._2 + "\t\tNo Welsh Translation Found")
      {existingTranslation =>
        checkEnglishMessageChanged(existingTranslation, enMessage)
      }

      csvFile.println(output)
    }

    csvFile.close()
  }

  def checkEnglishMessageChanged(translation: translationLine, enMessage: messageLine): String = {
    if(translation._2._1 == enMessage._2){
      if(translation._2._2 == ""){
        translation._1 + "\t" + enMessage._2 + "\t\t" + "Welsh Translation Empty" //English changed
      }
      else {
        translation._1 + "\t" + translation._2._1 + "\t" + translation._2._2  + "\tEnglish message unchanged"//English unchanged
      }
    }
    else{
      translation._1 + "\t" + enMessage._2 + "\t\t" + "Message changed (previous message was: "+ translation._2._1+ " / " +translation._2._2 + ")" //English changed
    }
  }


  def fetchMessages(lang:String):Map[String, String] = {
    val lines = for (line <- linesFromFile(lang)) yield line

    lines.flatMap{ line =>
      splitKeyValues(line, "=").map(line => line._1 -> line._2._1)
    }.toMap
  }
}