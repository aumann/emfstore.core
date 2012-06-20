package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommandWithResult;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Handlers are the top level abstraction that clients should use execute actions within the EMFStore
 * workspace. They are responsible for calling the UI controllers and therefore provide
 * helper methods that can determine the currently selected element.
 * 
 * @author ovonwesen
 * @author emueller
 * 
 * @param <T> the return type of the handler
 */
public abstract class AbstractEMFStoreHandlerWithResult<T> extends AbstractHandler {

	private ExecutionEvent event;

	public T execute(ExecutionEvent event) throws ExecutionException {
		this.event = event;

		new EMFStoreCommandWithResult<T>() {
			@Override
			protected T doRun() {
				return handleWithResult();
			}
		}.run(false);

		return null;
	}

	public abstract T handleWithResult();

	protected ExecutionEvent getEvent() {
		return event;
	}

	public <T> T requireSelection(Class<T> clazz) throws RequiredSelectionException {
		return EMFStoreHandlerUtil.requireSelection(getEvent(), clazz);
	}

	public <T> T getSelection(Class<T> clazz) {
		return EMFStoreHandlerUtil.getSelection(getEvent(), clazz);
	}

	public Shell getShell() {
		return Display.getCurrent().getActiveShell();
	}
}
