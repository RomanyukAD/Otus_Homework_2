#language: ru
  Функция: Поиск нужного курса
    @Test1
    Структура сценария: Поиск курса по определенному названию, выбор курса
    Допустим Открытие "<browser>"
    Затем Поиск указанного курса "<name>"
      Примеры:
      |browser|name|
      |CHROME |Java|

