package medicalpatient.profile;

import medicalpatient.model.LocalDataBase;
import medicalpatient.model.User;

public class AgentProfile {


    public User getUser(){
        return LocalDataBase.getInstance(null).getUser();
    }
}
