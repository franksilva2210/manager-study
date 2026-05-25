package app.ui.main;

import app.application.text.TextDTO;
import app.application.text.TextMapper;
import app.domain.text.Text;
import app.infra.text.TextRepository;

public class EditorTextService {

    private TextRepository textRepository = new TextRepository();

    public TextDTO save(TextDTO textDto) {
        Text text = TextMapper.toEntity(textDto);
        return TextMapper.toDTO(textRepository.save(text));
    }

}
