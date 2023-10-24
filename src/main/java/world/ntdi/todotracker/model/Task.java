package world.ntdi.todotracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Task {
    private String m_name;
    private String m_description;
    private String m_dueDate;
}
