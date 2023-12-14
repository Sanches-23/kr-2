import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TaskSelectorPanel extends JPanel implements ActionListener, TaskChangeObserver{
//    private JComboBox selector = new JComboBox();
    private JComboBox<Task> selector = new JComboBox<>(); //modified
    private TaskChangeObservable notifier;
    private JButton toggleButton; //modified
    private boolean displayNotesOnly = false; //modified
    public TaskSelectorPanel(TaskChangeObservable newNotifier){
        notifier = newNotifier;
        createGui();
    }
    public void createGui(){
        selector = new JComboBox<>();
        selector.addActionListener(this);
        toggleButton = new JButton("Toggle selector"); //modified
        toggleButton.addActionListener(this); //modified

        selector.setRenderer((list, task, index, isSelected, cellHasFocus) -> { //modified
            DefaultListCellRenderer renderer = new DefaultListCellRenderer();
            renderer.getListCellRendererComponent(list, task, index, isSelected, cellHasFocus);
            if (task != null) {
                renderer.setText(task.toStringSelector());
            }
            return renderer;
        });
        add(toggleButton); //modified
        add(selector);
    }
    public void actionPerformed(ActionEvent evt){
        if (evt.getSource() == selector) {
            notifier.selectTask((Task)selector.getSelectedItem());
        } else if (evt.getSource() == toggleButton) { //modified
            displayNotesOnly = !displayNotesOnly;
            System.out.println(displayNotesOnly);
            updateSelector();
        }
    }
    public void setTaskChangeObservable(TaskChangeObservable newNotifier){
        notifier = newNotifier;
    }
    public void taskAdded(Task task){
        selector.addItem(task);
    }
    public void taskChanged(Task task){ }
    public void taskSelected(Task task) {
        task.setDisplayNotesOnly(displayNotesOnly);  //modified
    }
    private void updateSelector() { //modified
        Task selectedTask = (Task) selector.getSelectedItem();
        if (selectedTask != null) {
            selector.removeItem(selectedTask);
            selector.addItem(selectedTask);
            selector.setSelectedItem(selectedTask);
        }
    }
}












//public class TaskSelectorPanel extends JPanel implements ActionListener, TaskChangeObserver {
//    private JComboBox selector = new JComboBox();
//    private TaskChangeObservable notifier;
//    private boolean displayBoth = true;
//    private JToggleButton toggleButton;
//
//    public TaskSelectorPanel(TaskChangeObservable newNotifier) {
//        notifier = newNotifier;
//        createGui();
//    }
//
//    public void createGui() {
//        selector = new JComboBox();
//        selector.addActionListener(this);
//
//        toggleButton = new JToggleButton("Toggle Display");
//        toggleButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                displayBoth = !displayBoth;
//                updateSelector();
//            }
//        });
//
//        add(selector);
//        add(toggleButton);
//
//        updateSelector();
//    }
//
//    private void updateSelector() {
////        selector.removeAllItems();
////
////        if (displayBoth) {
////            notifier.getTasks().forEach(task -> selector.addItem(task));
////        } else {
////            notifier.getTasks().forEach(task -> selector.addItem(task.getNotes()));
////        }
//    }
//
//    public void actionPerformed(ActionEvent evt) {
//        notifier.selectTask((Task) selector.getSelectedItem());
//    }
//
//    public void setTaskChangeObservable(TaskChangeObservable newNotifier) {
//        notifier = newNotifier;
//    }
//
//    public void taskAdded(Task task) {
//    }
//
//    public void taskChanged(Task task) {
//    }
//
//    public void taskSelected(Task task) {
//    }
//}
//    public void actionPerformed(ActionEvent evt){
//        if (evt.getSource() == selector) {
//            Task selectedTask = (Task) selector.getSelectedItem();
//            if (selectedTask != null) {
//                selectedTask.setDisplayNotesOnly(displayNotesOnly);
//                notifier.selectTask(selectedTask);
//            }
//        } else if (evt.getSource() == toggleButton) {
//            displayNotesOnly = !displayNotesOnly;
//            System.out.println(displayNotesOnly);
//            updateSelector();
//        }
//    }
