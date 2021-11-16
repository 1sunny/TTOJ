package one.sunny.judgeservice.entity;


public class LanguageConfig {
    private String template;
    private Compile compile;
    private Run run;

    public LanguageConfig(String type){
        this.setCompile(new Compile("cpp"));
        this.setRun(new Run("cpp"));
        this.setTemplate("");
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Compile getCompile() {
        return compile;
    }

    public void setCompile(Compile compile) {
        this.compile = compile;
    }

    public Run getRun() {
        return run;
    }

    public void setRun(Run run) {
        this.run = run;
    }

    @Override
    public String toString() {
        return "LanguageConfig{" +
                "template='" + template + '\'' +
                ", compile=" + compile +
                ", run=" + run +
                '}';
    }
}
