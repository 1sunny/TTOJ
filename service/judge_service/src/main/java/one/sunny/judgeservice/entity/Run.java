package one.sunny.judgeservice.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Run {
    private String command;
    private Map seccomp_rule;
    private List<String> env;

    public Run(String type){
        if(type == "cpp"){
            this.setCommand("{exe_path}");
            HashMap<String, String> map = new HashMap<>();
            map.put("Standard IO", "c_cpp");
            map.put("File IO", "c_cpp_file_io");
            this.setSeccomp_rule(map);
            List<String> env = new ArrayList<>();
            env.add("LANG=en_US.UTF-8");
            env.add("LANGUAGE=en_US:en");
            env.add("LC_ALL=en_US.UTF-8");
            this.setEnv(env);
        }
    }

    @Override
    public String toString() {
        return "Run{" +
                "command='" + command + '\'' +
                ", seccomp_rule=" + seccomp_rule +
                ", env=" + env +
                '}';
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Map getSeccomp_rule() {
        return seccomp_rule;
    }

    public void setSeccomp_rule(Map seccomp_rule) {
        this.seccomp_rule = seccomp_rule;
    }

    public List<String> getEnv() {
        return env;
    }

    public void setEnv(List<String> env) {
        this.env = env;
    }
}
