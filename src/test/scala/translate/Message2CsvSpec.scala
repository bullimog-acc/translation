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

import org.scalatest._
import util.WrappedPrintWriter


class Message2CsvSpec extends FlatSpec with Matchers {

  val inputCsvFile = "existingTranslations.csv"
  val inputMessagesFile = "Messages.en"

  class FakeWrappedPrintWriter(fileName: String) extends WrappedPrintWriter(fileName: String) {
    var output:String = ""
    override def println(line: String) = {output += line + "\n"}
    override def close() = {}
  }


  object FakeMessage2Csv extends Message2Csv("") {
    override def linesFromFile(fileName: String): Iterator[String] = {

      if(fileName == inputCsvFile) {
        val line1 = "key 1 \t English message 1" +        "\t Welsh message 1"
        val line2 = "key 2 \t English message 2" +"\t Welsh message 2"
        val line3 = "key 3 \t English message 3" +        "\t"
        Iterator(line1, line2, line3)
      }
      else{
        val line1 = "key 1 = English message 1"
        val line2 = "key 2 = English updated message 2"
        val line3 = "key 3 = English message 3"
        val line4 = "key 4 = English message 4"
        Iterator(line1, line2, line3, line4)
      }
    }

    override lazy val csvFile = new FakeWrappedPrintWriter("temp")
  }


  "Message2csv" should
    "read and map the english message file values, separated by an =" in {
    val result = FakeMessage2Csv.fetchMessages("messages.en")
    result shouldBe Map("key 1" -> "English message 1",
                        "key 2" -> "English updated message 2",
                        "key 3" -> "English message 3",
                        "key 4" -> "English message 4")
  }


  "Message2csv" should
  "read the en message file, find message in tracked csv, and write the result into a new csv file " in {
    val result = FakeMessage2Csv.messages2csv()
    FakeMessage2Csv.csvFile.output shouldBe "Key\tEnglish\tWelsh\tComments\n" +
                                            "key 1\tEnglish message 1\tWelsh message 1\tEnglish message unchanged\n" +    // Already translated
                                            "key 2\tEnglish updated message 2\t\tMessage changed (previous message was: English message 2 / Welsh message 2)\n" +   // Message changed
                                            "key 3\tEnglish message 3\t\tWelsh Translation Empty\n" +                     // Welsh empty in csv
                                            "key 4\tEnglish message 4\t\tNo Welsh Translation Found\n"                    // New Message
  }
}




