Проект Spring security, настроен JWT токен при создании пользователя. Подключение к docker бд: docker run --name users-db -p 5432:5432 -e POSTGRES_DB=users -e POSTGRES_USER=users -e POSTGRES_PASSWORD=users postgres:16

Создание пользователя через postman: https://localhost:8443/auth/register POST 
{
  "username": "...",
  "password": "..."
{

Логин его https://localhost:8443/auth/login POST :

{
  "username": "который вводили в первом случае",
  "password": "которые вводили в первом случае "
}

Защита токена:

  1. Токен подписан и проверяется
  2. Используется безопасный SecretKey
  3. Реализована валидация подписи
  4. У токена короткое TTL
  5. Токен передаётся через HTTPS

При login, токен сразу меняется
Когда срок действия токена истекает, то создаётся новый
Также настроил https и теперь токены передаются по защищённому соединению
