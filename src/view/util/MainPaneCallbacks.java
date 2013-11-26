package view.util;


public interface MainPaneCallbacks extends Updatable {

    public void changeCenterPane(final GenericPane<?> pane);

    public void setCreateConferenceButtonVisible(final boolean visible);


}