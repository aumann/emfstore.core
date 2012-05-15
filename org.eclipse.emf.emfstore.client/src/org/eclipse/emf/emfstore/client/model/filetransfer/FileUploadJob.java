/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.filetransfer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.exceptions.FileTransferException;
import org.eclipse.emf.emfstore.server.filetransfer.FileChunk;
import org.eclipse.emf.emfstore.server.filetransfer.FilePartitionerUtil;
import org.eclipse.emf.emfstore.server.filetransfer.FileTransferInformation;
import org.eclipse.emf.emfstore.server.model.FileIdentifier;

/**
 * File Upload Job class is responsible for uploading files to the server in the Eclipse Worker thread.
 * 
 * @author pfeifferc, jfinis
 */
public class FileUploadJob extends FileTransferJob {

	/**
	 * Default constructor. Only used internally; only the FileTransferManager may create such jobs.
	 * 
	 * @param transferManager the transfer manager which created the job
	 * @param fileId the identifier of the file to be uploaded
	 * @param transferVisibleToUser progress bar yes/no
	 * @throws FileTransferException any error occurring during a file transfer is wrapped in a file transfer exception
	 */
	public FileUploadJob(FileTransferManager transferManager, FileIdentifier fileId, boolean transferVisibleToUser)
		throws FileTransferException {
		super(transferManager, new FileTransferInformation(fileId, (int) transferManager.getCache()
			.getCachedFile(fileId).length()), "File Upload");
		setUser(transferVisibleToUser);
		setFile(getCache().getCachedFile(fileId));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			// get values for the required fields
			getConnectionAttributes();

			// executes the file transfer (loop)
			if (!executeTransfer(monitor)) {
				return Status.CANCEL_STATUS;
			}
		} catch (EmfStoreException e) {
			setException(e);
			monitor.setCanceled(true);
			monitor.done();
			return Status.CANCEL_STATUS;
		}
		return Status.OK_STATUS;
	}

	/**
	 * Retrieves and writes the file chunks until the end flag is set in a file chunk.
	 * 
	 * @param fileChunk file chunk
	 * @param monitor showing the progress of the transfer.
	 * @throws EmfStoreException if any error occurs in the emf store
	 */
	private boolean executeTransfer(IProgressMonitor monitor) throws EmfStoreException {
		FileChunk fileChunk;
		initializeMonitor(monitor);
		long transmitted = 0;
		do {
			fileChunk = FilePartitionerUtil.readChunk(getFile(), getFileInformation());
			getConnectionManager().uploadFileChunk(getSessionId(), getProjectId(), fileChunk);
			transmitted += fileChunk.getData().length;
			monitor.worked(1);
			monitor.subTask("Sending file " + getFileInformation() + ": " + transmitted + " of "
				+ getFileInformation().getFileSize() + " bytes transmitted");
			incrementChunkNumber();
			if (isCanceled()) {
				return false;
			}
		} while (!fileChunk.isLast());
		getTransferManager().removeWaitingUpload(getFileId());
		return true;
	}

}
