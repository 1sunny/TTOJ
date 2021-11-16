package one.sunny.judgeservice.entity;

public class JudgeBean {
    private String src;
    private Integer max_cpu_time;
    private Integer max_memory;
    private String test_case_id;
    private LanguageConfig language_config;
    private Boolean output;
 // TODO optimal it
    public JudgeBean(String type,
              String src,
              Integer max_cpu_time,
              Integer max_memory,
              String test_case_id,
              Boolean output){
        this.setSrc(src);
        this.setMax_cpu_time(max_cpu_time);
        this.setMax_memory(max_memory);
        this.setTest_case_id(test_case_id);
        this.setOutput(output);
        this.setLanguage_config(new LanguageConfig(type));
    }

    @Override
    public String toString() {
        return "JudgeBean{" +
                "src='" + src + '\'' +
                ", max_cpu_time=" + max_cpu_time +
                ", max_memory=" + max_memory +
                ", test_case_id='" + test_case_id + '\'' +
                ", language_config=" + language_config +
                ", output=" + output +
                '}';
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getMax_cpu_time() {
        return max_cpu_time;
    }

    public void setMax_cpu_time(Integer max_cpu_time) {
        this.max_cpu_time = max_cpu_time;
    }

    public Integer getMax_memory() {
        return max_memory;
    }

    public void setMax_memory(Integer max_memory) {
        this.max_memory = max_memory;
    }

    public String getTest_case_id() {
        return test_case_id;
    }

    public void setTest_case_id(String test_case_id) {
        this.test_case_id = test_case_id;
    }

    public LanguageConfig getLanguage_config() {
        return language_config;
    }

    public void setLanguage_config(LanguageConfig language_config) {
        this.language_config = language_config;
    }

    public Boolean getOutput() {
        return output;
    }

    public void setOutput(Boolean output) {
        this.output = output;
    }
}
