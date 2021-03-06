Тестовое задание для SimbirSoft
=========

Что требуется получить:
```
Приложение, которое позволяет скачивать произвольную HTML-страницу
посредством HTTP-запроса на жесткий диск компьютера и выдает статистику по
количеству уникальных слов в консоль.
```

Требования к приложению
```
1. В качестве входных данных в приложение принимает строку с адресом
web-страницы. Пример входной строки: https://www.simbirsoft.com/ 
2. Приложение разбивает текст страницы на отдельные слова с помощью
списка разделителей.
Пример списка:
{' ', ',', '.', '! ', '?','"', ';', ':', '[', ']', '(', ')', '\n', '\r', '\t'}
3. В качестве результата работы пользователь должен получить статистику по
количеству уникальных слов в тексте.
Пример:
РАЗРАБОТКА -1
ПРОГРАММНОГО - 2
ОБЕСПЕЧЕНИЯ - 4
4. Приложение должно быть реализовано с помощью стандартных классов(не стоит добавлять
собственные реализации списков, словарей и т.п.)
5. Приложение написано в соответствии с принципами ООП
6. Приложение написано на языке выбранного направления (Java, C#, Golang)
```

Бонусом будет:
```
1. Хороший стиль кода, приближенный к общепринятым стандартам форматирования кода
2. Использование паттернов проектирования
3. Логирование ошибок в файл
4. Сохранение статистики в базу данных
5. Возможность расширяемости проекта и многоуровневую архитектуру
6. Тесты
```

Как работать с программой:
```
Для запуска программы используйте команду:
- для Mac и Linux: ./gradlew run
- для Windows: gradlew run
```
