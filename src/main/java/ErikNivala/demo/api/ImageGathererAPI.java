package ErikNivala.demo.api;

import ErikNivala.demo.model.Person;
import ErikNivala.demo.service.ImageGatheringService;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RequestMapping("api/v1/ImageGatherer")
@RestController
public class ImageGathererAPI {


    @PostMapping
    public void downloadImagesFromSubreddit( @Valid @NonNull @RequestBody Person person) {
       // ImageGatheringService.addPerson(person);
    }

}
