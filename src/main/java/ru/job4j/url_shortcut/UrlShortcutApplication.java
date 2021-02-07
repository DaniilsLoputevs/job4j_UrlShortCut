package ru.job4j.url_shortcut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO - Техническое задание:
 * TODO - 1. Регистрация сайта.
 * Сервисом могут пользоваться разные сайты. Каждому сайту выдается пару пароль и логин.
 * Чтобы зарегистрировать сайт в систему нужно отправить запроса.
 * req: POST /registration   {site : "job4j.ru"}
 * res: {registration : true/false, login: УНИКАЛЬНЫЙ_КОД, password : УНИКАЛЬНЫЙ_КОД}
 * * Флаг registration указывает, что регистрация выполнена или нет, то есть сайт уже есть в системе.
 * true - зареган сейчас, false - уже был, Exception - что-то, не так.
 * TODO - 2. JWT
 * POST /login -- обмен login & password ->> JWT
 * TODO - 3. Регистрация URL.
 * req: POST /convert   {url: "https://job4j.ru/TrackStudio/task/8993?thisframe=true"}
 * res: {code: УНИКАЛЬНЫЙ_КОД}     Пример: {code: ZRUfD2 }
 * TODO - 4. Переадресация. Выполняется без авторизации.
 * Когда сайт отправляет ссылку с кодом в ответ нужно вернуть ассоциированный адрес и статус 302.
 * req: GET /redirect/УНИКАЛЬНЫЙ_КОД
 * res: HTTP CODE - 302 REDIRECT URL
 * TODO - 5. Статистика.
 * В сервисе считается количество вызовов каждого адреса.
 * По сайту можно получить статистку всех адресов и количество вызовов этого адреса.
 * req: GET /statistic
 * res:
 * {
 *     {url : URL, total : 0},
 *     {url : "https://job4j.ru/TrackStudio/task/23", total : 103}
 * }
 */
@SpringBootApplication
public class UrlShortcutApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(UrlShortcutApplication.class, args);
        System.err.println("APP RUN");
    }
    
}
