package one.sunny.judgeservice.entity;

public class Compile {
    public String src_name;
    public String exe_name;
    public Integer max_cpu_time;
    public Integer max_real_time;
    public Integer max_memory;
    public String compile_command;

    public Compile(String type){
        if(type == "cpp"){
            this.setSrc_name("main.cpp");
            this.setExe_name("main");
            this.setMax_cpu_time(10000);
            this.setMax_real_time(20000);
            this.setMax_memory(1073741824);
            this.setCompile_command("/usr/bin/g++ -DONLINE_JUDGE -O2 -w -fmax-errors=3 -std=c++14 {src_path} -lm -o {exe_path}");
        }
    }

    @Override
    public String toString() {
        return "Compile{" +
                "src_name='" + src_name + '\'' +
                ", exe_name='" + exe_name + '\'' +
                ", max_cpu_time=" + max_cpu_time +
                ", max_real_time=" + max_real_time +
                ", max_memory=" + max_memory +
                ", compile_command='" + compile_command + '\'' +
                '}';
    }

    public String getSrc_name() {
        return src_name;
    }

    public void setSrc_name(String src_name) {
        this.src_name = src_name;
    }

    public String getExe_name() {
        return exe_name;
    }

    public void setExe_name(String exe_name) {
        this.exe_name = exe_name;
    }

    public Integer getMax_cpu_time() {
        return max_cpu_time;
    }

    public void setMax_cpu_time(Integer max_cpu_time) {
        this.max_cpu_time = max_cpu_time;
    }

    public Integer getMax_real_time() {
        return max_real_time;
    }

    public void setMax_real_time(Integer max_real_time) {
        this.max_real_time = max_real_time;
    }

    public Integer getMax_memory() {
        return max_memory;
    }

    public void setMax_memory(Integer max_memory) {
        this.max_memory = max_memory;
    }

    public String getCompile_command() {
        return compile_command;
    }

    public void setCompile_command(String compile_command) {
        this.compile_command = compile_command;
    }
}
