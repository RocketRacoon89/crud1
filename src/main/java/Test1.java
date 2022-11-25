import com.Mike.crud.model.Skill;
import com.Mike.crud.model.Status;
import com.Mike.crud.repository.database.DBconnect;
import com.Mike.crud.repository.database.DbSkillRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class Test1 {
    public static void main(String[] args) throws SQLException {
        DbSkillRepositoryImpl dbSkillRepository = null;
        dbSkillRepository.getById(1);

    }
}
