package chat.cyber.controller;

import chat.cyber.controller.dtos.EmailUserDTO;
import chat.cyber.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/search/")
public class SearchController {

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    private SearchService searchService;


    @PostMapping(path = "email", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> searchEmail(@RequestBody EmailUserDTO data){

        return searchService.searchByEmail(data);

    }
}
