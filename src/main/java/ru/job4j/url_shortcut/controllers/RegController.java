package ru.job4j.url_shortcut.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.url_shortcut.models.Site;
import ru.job4j.url_shortcut.services.SiteManager;

@RestController
@CrossOrigin(value = {"*"})
public class RegController {
    public static final String CONTROLLER_REGISTRATION = "/shortcut/registration/";
    @Autowired
    private SiteManager siteManager;
    
    
    /**
     * TODO - 1. Регистрация сайта.
     * Сервисом могут пользоваться разные сайты. Каждому сайту выдается пару пароль и логин.
     * Чтобы зарегистрировать сайт в систему нужно отправить запроса.
     * req: POST /registration   {site : "job4j.ru"}
     * res: {registration : true/false, login: УНИКАЛЬНЫЙ_КОД, password : УНИКАЛЬНЫЙ_КОД}
     * * Флаг registration указывает, что регистрация выполнена или нет, то есть сайт уже есть в системе.
     * true - зареган сейчас, false - уже был, Exception - что-то, не так.
     *
     * @return SiteDTO.
     */
    @PostMapping(CONTROLLER_REGISTRATION)
    public ResponseEntity<SiteDTO> reg(@RequestBody Site site) {
        final String domainUrl = site.getDomainUrl();
        return new ResponseEntity<>(
                (siteManager.contains(domainUrl))
                ? new SiteDTO(siteManager.getByDomainUrl(domainUrl), false)
                : new SiteDTO(siteManager.registerSite(domainUrl), true),
                HttpStatus.OK);
    }
    
    
    /* Local things */
    
    
    /* Lombok */
    @AllArgsConstructor
    @Getter
    @Setter
    private static class SiteDTO {
        private final Site originalSite;
        /**
         * true - this site registered at this request.
         * false - this site was registered before.
         */
        private final boolean registered;
    }
    
}
