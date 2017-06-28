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

import util.{CsvReader, WrappedPrintWriter}


object Csv2Message extends Csv2Message


class Csv2Message extends CsvReader{

  lazy val welshMessages = new WrappedPrintWriter("_messages.cy")

  def csv2Messages(csvFilename:String):Unit = {

    val existingTranslations = readFromCsv(csvFilename)

    existingTranslations.map{translation =>
      welshMessages.println(translation._1 + "="  + translation._2._2)
    }
    welshMessages.close()
  }
}