package app.application.topic;

public class TopicDtoService {

    public void copy(TopicDTO topicCurrentDto, TopicDTO topicUpdatedDto) {
        topicCurrentDto.setTitle(topicUpdatedDto.getTitle());
    }
}
