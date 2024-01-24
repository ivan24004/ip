package model.commands;
import io.Message;
import utils.RemiError;

@FunctionalInterface
public interface Command {
    /**
     *
     * @param args the arguments of the message (this excludes the first word of the command)
     * @return the message to be outputted
     */
    Message run(String args) throws RemiError;
}
