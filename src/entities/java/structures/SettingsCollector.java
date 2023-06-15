package structures;

public class SettingsCollector extends List<Settings> {


    private MessageSettings currentMessageSettings;

    private Settings currentSettings;


    // override funzione 'add' e assegnazione alle corrette variabili
    @Override
    public void add(Settings element) {
        super.add(element);

        if (MessageSettings.class.isAssignableFrom(element.getClass()))
            this.currentMessageSettings = (MessageSettings) element;

        if (element.getClass() == Settings.class)
            this.currentSettings = element;
    }

    public MessageSettings getMessageSettings() {
        return this.currentMessageSettings;
    }

    public Settings getCurrentSettings() {
        return this.currentSettings;
    }


}
