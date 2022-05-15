Проект "Банковская система"

Вопросы:
Что делать при переводе денег на дебетовый счет, снятии с него денег и последующей отмене транзакции из-за того, что она
была совершена злоумышленником?
(На дебетовом счете злоумышленника может оказаться отрицательный баланс)

Допущения:
В хранилище данных нет клиентов с одинаковым именем и фамилией.
В хранилище данных нет банков с одинаковым названием.

Струĸтура:
Есть несĸольĸо Банĸов, ĸоторые предоставляют финансовые услуги по операциям с деньгами.
В банĸе есть Счета и Клиенты. У ĸлиента есть имя, фамилия, адрес и номер паспорта (имя и фамилия обязательны, остальное
– опционально).
Счета бывают трёх видов: Дебетовый счет, Депозит и Кредитный счет. Каждый счет принадлежит ĸаĸому-то ĸлиенту.
Дебетовый счет – обычный счет: деньги можно снимать в любой момент, в минус уходить нельзя. Комиссий нет.
Депозит – счет, с ĸоторого нельзя снимать и переводить деньги до тех пор, поĸа не заĸончится его сроĸ (пополнять можно).
Комиссий нет.
Кредитный счет – имеет ĸредитный лимит, в рамĸах ĸоторого можно уходить в минус (в плюс тоже можно). Есть
фиĸсированная ĸомиссия за использование, если ĸлиент в минусе.

Детали реализации:

1) Модуль "Банки":
   Должно быть создано хранилище информации о существующих банках (Bank.csv).
   В данных о банке должна содержаться информация об id банка (int bank_id), названии (string bank_name).

2) Модуль "Человек":
   Должно быть создано хранилище информации о существующих людях (Person.csv).
   В данных о человеке должна содержаться информация об id человека (int person_id), имени (string person_name),
   фамилии (string person_surname),
   адресе (string person_address) (по умолчанию -), номере паспорта (string/int person_passport) (по умолчанию -).

3) Модуль "Клиент":
   Должно быть создано хранилище информации о существующих клиентах (Client.csv).
   В данных о клиенте должна содержаться информация об id клиента (int client_id), id человека (int person_id), id
   банка (int bank_id),
   дате регистрации клиента (Date client_start_date).

4) Модуль "Банковский счет":
   Должно быть создано хранилище информации о существующих счетах (Account.csv).
   Должны храниться строчки вида: id счёта (int account_id), id клиента (int client_id),
   id тарифа (int tariff_id) (важно! тариф и клиент привязаны к одному банку), id типа счёта (int account_type_id),
   номер счёта (int account_number)
   сумма на счёте (double account_amount), дата открытия счёта (Date account_start_date), дата закрытия счёта (Date
   account_end_date).

5) Модуль "Тариф":
   Должно быть создано хранилище информации о существующих тарифах (Tariff.csv).
   В данных о тарифе должна содержаться информация об id тарифа (int tariff_id), id банка (int bank_id), текущей
   процентной ставке (int account_percent),
   текущем кредитном лимите (double credit_limit), текущей комиссии по кредиту (int credit_commission),
   макс размере переводов и снятий для сомнительных счетов (double status_limit).

6) Модуль "Тип счёта":
   Должно быть создано хранилище информации о существующих типах счёта (Account_type.csv).
   Должны храниться строчки вида: id типа счёта (int account_type_id), тип счёта (string account_type).
   1;credit
   2;debit
   3;deposit

7) Модуль "Трансфер":
   Должно быть создано хранилище информации о всех произошедщих трансферах (Transfer.csv).
   Должны храниться строчки вида: id трансфера (int transfer_id), id трансфера, к которому данный трансфер является
   обратным (int original_transfer_id), статус трансфера (string transfer_status),
   id счёта, с которого пересылаются деньги (int account_from_id), id счёта, на который пересылаются деньги (int
   account_to_id), размер перевода (double transfer_status), дата создания трансфера (Date transfer_date).
   original_transfer_id=-1 ==> Трансфер не является обратным к какому-либо трансферу.
   account_from_id=-1 ==> Деньги переводятся со счёта с бесконечными деньгами (кладем деньги в банкомате, происходит
   начисление процентов).
   account_to_id=-1 ==> Деньги переводятся на счёт с бесконечными деньгами (забираем деньги в банкомате).

Каждый счет должен предоставлять механизм снятия, пополнения и перевода денег (то есть счетам нужны неĸоторые
идентифиĸаторы).
Клиент должен создаваться по шагам. Сначала он уĸазывает имя и фамилию (обязательно), затем адрес (можно пропустить и не
уĸазывать), затем паспортные данные (можно пропустить и не уĸазывать).
Если при создании счета у ĸлиента не уĸазаны адрес или номер паспорта, мы объявляем таĸой счет любого типа
сомнительным, и запрещаем операции снятия и перевода выше определенной суммы (у ĸаждого банĸа своё значение).
Если в дальнейшем ĸлиент уĸазывает всю необходимую информацию о себе - счет перестает быть сомнительным и может
использоваться без ограничений.
Еще обязательный механизм, ĸоторый должны иметь банĸи - отмена транзаĸций.
Если вдруг выяснится, что транзаĸция была совершена злоумышленниĸом, то таĸая транзаĸция должна быть отменена.

Версии:

Версия 2:

1) Удалены лишние бинари и идеевские файлы, изменён файл gitignore.
2) Изменена UML диаграмма.
3) Изменена связь трансфер - клиент.
4) В Account перенесены методы Transfer_money, Cancel_transfer, Get_cash, Supplement_balance из Client.
5) Метод получения всех банков из класса Bank удален. Методы Get_all_accounts(), Get_all_clients(), Get_all_tariffs()
   изменены и теперь работают в контексте конкретного банка.
6) GetBank_id добавлен в класс Tariff.
7) Static добавлено методам класса Transfer.

Версия 3:

1) Методы Save в Storage сделаны статическими, исправление отражено в UML диаграмме.
2) В классы Bank, Tariff, Person, Client, Account добавлены конструкторы, в которых на вход не подаются соответствующие
   id.
   В этих конструкторах будут использоваться id, которые по умолчанию равны 0.
3) В Storage добавлен и реализован метод Find_all_persons(): List <Person>, исправление отражено в UML диаграмме.
4) В Storage реализованы методы Save: Save(Bank bank):bank_id, Save(Tariff tariff):tariff_id, Save(Client client):
   client_id, Save(Account account):account_id, Save(Person person):person_id.
   Save может добавлять новый объект в хранилище. Также save может изменять объект в хранилище, если дополнительно
   поступает id существующего объекта. Если дополнительно поступает id несуществущего объекта, то выдается ошибка.
5) Storage.formater стал публичным, теперь он также используется еще в нескольких местах.

Версия 4:

1) Реализован метод Add_tariff(Tariff tariff): tariff_id в классе Bank. Тариф можно сохранить только для выбранного
   банка.
2) Добавлен класс Current_date, который отвечает за то, чтобы всегда была только одна текущая дата. В классе реализуется
   паттерн Singleton (Одиночка).
3) Класс Current_date добавлен на UML диаграмму.
4) Реализован метод Add_client(Person person): client_id в классе Bank. Клиент создается с учетом текущей даты.
   Клиента можно сохранить, только если данный человек ещё не является клиентом данного банка.
5) Реализован метод Create_client(bank_id): client_id в классе Person. Клиент создается с учетом текущей даты.
   Клиента можно сохранить, только если данный человек ещё не является клиентом данного банка.
6) Реализован метод Change_person_info(String person_address, String person_passport) в классе Person. Метод позволяет
   изменить адрес и паспорт человека.
7) Реализован метод Client_status_checker() в классе Client. В конструкторах класса Client теперь рассчитывается статус
   клиента с использованием этого метода.
8) Добавлено хранение информации о трансферах. В трансере теперь также есть информация о дате его проведения.
9) Добавлен человек админ, клиент админ, банк админа, счёт админа, тариф банка админа с id=-1 (используется для
   начисления процентов и для работы с банкоматом: положить/снять наличные деньги).
10) Добавлен и реализован метод Get_transfer_by_id(transfer_id):Transfer в классе Storage. Метод добавлен в UML
    диаграмму.
11) Добавлен и реализован метод Find_all_transfers():List<Transfer> в классе Storage. Метод добавлен в UML диаграмму.
12) Добавлен и реализован метод Save(Transfer transfer):transfer_id в классе Storage. Метод добавлен в UML диаграмму.

Версия 5:

1) Реализован метод Create_account(string account_type, int account_number):account_id в классе Client.
   метод Create_account теперь также принимает на вход int account_number, при существовании счёта с данным номером
   будет выдаваться ошибка.
   Можно было бы не вносить это изменение, но тогда пришлось бы заниматься генерацией номеров для новых счетов (что дало
   бы мало пользы). Изменение отражено на UML диаграмме.
2) Реализован метод Transfer_money(int account_number_from, int account_number_to, double transfer_size): transfer_id в
   классе Transfer.
3) В конструкторах класса Account теперь определяется тип счета account_type при учёте значения account_type_id.
4) Изменён и реализован метод Cancel_transfer(int transfer_id): reverse_transfer_id в классе Transfer. Изменение
   отражено на UML диаграмме.
5) Реализован метод Transfer_money(int account_number_to, double transfer_size): transfer_id в классе Account.
6) Реализован метод Get_cash(double amount) в классе Account.
7) Реализован метод Supplement_balance(double amount) в классе Account.
8) Удалён метод Cancel_transfer(transfer_id) в классе Account. Изменение отражено на UML диаграмме.
9) В методах Find... и Get_..._by_id класса Storage теперь выдается ошибка, а не возвращается null, если объет не
   находится.
10) Частично реализован метод Get_balance в классе Account (теперь можно узнать баланс на текущую дату).

Версия 6:

1) Метод Find(Integer client_id, String account_type):List<Account> в классе Storage заменен на Find_client_accounts(
   Integer client_id):List<Account>. Изменение отражено на UML диаграмме.
2) Реализована возможность запуска программы. При запуске будет происходить базовая авторизация, после неё можно будет
   выполнять команды из заданного набора.
   Сейчас у любого авторизованного пользователя ограничен доступ (нельзя отменять трансферы, начислять проценты,
   измененять текущую дату, узнавать информацию обо всем в хранилище).

Версия 7:

1) Выполнено автоформатирование кода. Также были удалены лишние импорты.

Версия 8:

1) Добавлен кастомный эксепшен ObjectAlreadyExistsException.
2) Общие Exception заменены на более подходящие во всей программе.
3) Изменён метод Create_account в классе Client. Часть его функциональности вынесена в новый метод Check_new_account в
   классе Client и в методы Create в классах-наследниках Account.
4) Работа с базовым временем действия депозита изменена, теперь находится в классе Deposit_account.
5) Добавлены методы Check_new_transfer_accounts_exist и Check_new_transfer_money_available в класс Transfer. Методы
   переняли часть функциональности у метода Transfer_money из этого же класса.
6) Исправлена UML диаграмма.
7) Некоторые конструкторы в классах были упрощены. Теперь они используют друг друга, если это возможно.

Версия 9:

1) Реализован метод Close_account в классе Account.
2) Реализован метод Close_account в классе Client.
3) Добавлен метод Check_new_transfer_accounts_not_closed в класс Transfer, использование которого позволяет запретить
   операции с закрытыми счетами.
4) Исходные базы данных Account.csv и Transfer.csv были обновлены и теперь согласуются между собой.
5) Пользователь теперь может закрыть свой счёт с нулевым балансом и узнать информацию о типе и статусе счёта.
6) Реализован метод Get_balance(Date date) в классе Account.
7) Пользователь теперь может узнать количество денег на балансе счёта на конкретную дату.

Версия 10:

1) Удалён метод Skip_period в классе Main, т.к. его функционал реализовывается с помощью Set_current_date.
2) Реализован метод Calculate_percent(Date date) в классе Account.
3) Добавлены и реализованы методы Calculate_percent() в классах-наследниках Account.
4) В класс Deposit_account добавлен int account_percent.
5) Изменена UML диаграмма.

Версия 11:

1) Проект теперь собирается.
2) Добавлено описание способа установки проекта

Версия 12:

1) Логика работы с пользователем вынесена из main в новый класс Console_user_interface. Основной метод нового класса -
   startProgram.
2) Опции, доступные пользователю после авторизации, частично вынесены в отдельные методы в классе
   Console_user_interface.
3) В ресурсы добавлен файл Glossary.csv, в котором содержатся почти все возможные текстовые сообщения программы и текст
   исключений. В программе почти все строки заменены на переменные из Glossary.csv.
4) В класс Storage добавлен метод Find_at_glossary.
5) Изменена UML диаграмма.