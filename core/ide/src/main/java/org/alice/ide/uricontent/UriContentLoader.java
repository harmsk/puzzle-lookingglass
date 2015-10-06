/*******************************************************************************
 * Copyright (c) 2006, 2015, Carnegie Mellon University. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Products derived from the software may not be called "Alice", nor may
 *    "Alice" appear in their name, without prior written permission of
 *    Carnegie Mellon University.
 *
 * 4. All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement: "This product includes software
 *    developed by Carnegie Mellon University"
 *
 * 5. The gallery of art assets and animations provided with this software is
 *    contributed by Electronic Arts Inc. and may be used for personal,
 *    non-commercial, and academic use only. Redistributions of any program
 *    source code that utilizes The Sims 2 Assets must also retain the copyright
 *    notice, list of conditions and the disclaimer contained in
 *    The Alice 3.0 Art Gallery License.
 *
 * DISCLAIMER:
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.
 * ANY AND ALL EXPRESS, STATUTORY OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,  FITNESS FOR A
 * PARTICULAR PURPOSE, TITLE, AND NON-INFRINGEMENT ARE DISCLAIMED. IN NO EVENT
 * SHALL THE AUTHORS, COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, PUNITIVE OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING FROM OR OTHERWISE RELATING TO
 * THE USE OF OR OTHER DEALINGS WITH THE SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/
package org.alice.ide.uricontent;

import java.awt.Image;

/**
 * @author Dennis Cosgrove
 */
//todo: make more thread safe and more sophisticated
public abstract class UriContentLoader<T> {

	private static <T> void invokeOnEventDispatchThread( final GetContentObserver<T> observer, final T content ) {
		if( java.awt.EventQueue.isDispatchThread() ) {
			observer.completed( content );
		} else {
			javax.swing.SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
					observer.completed( content );
				}
			} );
		}
	}

	/*<lg>private</lg>*/public class Worker {
		private final java.util.List<GetContentObserver<T>> observers = edu.cmu.cs.dennisc.java.util.Lists.newCopyOnWriteArrayList();

		private final java.util.concurrent.FutureTask<T> futureTask;
		private boolean isStarted;

		private final java.util.concurrent.ExecutorService executorService = java.util.concurrent.Executors.newSingleThreadExecutor();

		public Worker() {
			this.futureTask = new java.util.concurrent.FutureTask<T>( new java.util.concurrent.Callable<T>() {
				@Override
				public T call() throws Exception {
					return loadContent();
				}
			} ) {
				@Override
				protected void done() {
					super.done();
					try {
						invokeObserversOnEventDispatchThread( this.get() );
					} catch( InterruptedException ie ) {
						throw new Error( ie.getMessage(), ie );
					} catch( java.util.concurrent.ExecutionException ee ) {
						throw new Error( ee.getMessage(), ee );
					} catch( java.util.concurrent.CancellationException ce ) {
						//pass
					}
				}
			};
		}

		public void addObserver( GetContentObserver<T> observer ) {
			this.observers.add( observer );
		}

		private T loadContent() throws Exception {
			this.isStarted = true;
			GetContentObserver<T> observer;
			synchronized( this.observers ) {
				if( this.observers.size() > 0 ) {
					observer = this.observers.get( 0 );
				} else {
					observer = null;
				}
			}
			if( observer != null ) {
				observer.workStarted();
			}
			try {
				return UriContentLoader.this.load();
			} finally {
				if( observer != null ) {
					observer.workEnded();
				}
			}
		}

		public void executeIfNecessary() {
			//note: this check may not be necessary
			if( this.isStarted() ) {
				//pass
			} else {
				this.executorService.execute( this.futureTask );
			}
		}

		public boolean isDone() {
			return this.futureTask.isDone();
		}

		public boolean isStarted() {
			return this.isStarted;
		}

		public T getContent() throws InterruptedException, java.util.concurrent.ExecutionException {
			return this.futureTask.get();
		}

		private void invokeObserversOnEventDispatchThread( T content ) {
			for( GetContentObserver<T> observer : this.observers ) {
				invokeOnEventDispatchThread( observer, content );
			}
		}
	};

	private Worker worker;

	public abstract java.net.URI getUri();

	protected abstract T load();

	protected boolean isWorkerValid() {
		return this.worker != null;
	}

	protected boolean isWorkerCachingAppropriate( MutationPlan intention ) {
		return intention == MutationPlan.PROMISE_NOT_TO_MUTATE;
	}

	protected void cacheWorkerIfAppropriate( MutationPlan intention, Worker worker ) {
		if( this.isWorkerCachingAppropriate( intention ) ) {
			this.worker = worker;
		} else {
			this.worker = null;
		}
	}

	private Worker getWorker( MutationPlan intention ) {
		Worker worker;
		if( this.isWorkerValid() ) {
			worker = this.worker;
		} else {
			worker = new Worker();
		}
		this.cacheWorkerIfAppropriate( intention, worker );
		return worker;
	}

	protected abstract T createCopyIfNecessary( T value );

	public static enum MutationPlan {
		PROMISE_NOT_TO_MUTATE,
		WILL_MUTATE
	};

	public synchronized Worker getContentOnEventDispatchThread( MutationPlan intention, GetContentObserver<T> observer ) throws Exception {
		Worker worker = this.getWorker( intention );
		if( worker.isDone() ) {
			T content = this.createCopyIfNecessary( worker.getContent() );
			invokeOnEventDispatchThread( observer, content );
		} else {
			worker.addObserver( observer );
			worker.executeIfNecessary();
		}
		return worker;
	}

	public T getContentWaitingIfNecessary( MutationPlan intention ) throws InterruptedException, java.util.concurrent.ExecutionException {
		Worker worker = this.getWorker( intention );
		worker.executeIfNecessary();
		return this.createCopyIfNecessary( worker.getContent() );
	}

	// <lg>
	private final ThumbnailContentWorker imageContentWorker = new ThumbnailContentWorker() {

		@Override
		protected Image loadThumbnail() {
			return UriContentLoader.this.loadThumbnail();
		}

	};

	public abstract String getTitle();

	public abstract String getDescription();

	protected abstract Image loadThumbnail();

	public synchronized Image getThumbnail( GetContentObserver<Image> observer ) {
		if( this.imageContentWorker.isThumbnailCached() ) {
			return this.imageContentWorker.getCachedThumbnail();
		} else {
			this.imageContentWorker.execute( observer );
			return null;
		}
	}

	public synchronized Image getThumbnail( org.lgna.croquet.views.AwtComponentView<?> component ) {
		return this.getThumbnail( component.getAwtComponent() );
	}

	public synchronized Image getThumbnail( java.awt.Component component ) {
		if( this.imageContentWorker.isThumbnailCached() ) {
			return this.imageContentWorker.getCachedThumbnail();
		} else {
			this.imageContentWorker.execute( new ImageRefreshObserver( component ) );
			return null;
		}
	}

	public Image getThumbnailWaitingIfNecessary() throws InterruptedException, java.util.concurrent.ExecutionException {
		if( this.imageContentWorker.isThumbnailCached() ) {
			return this.imageContentWorker.getCachedThumbnail();
		} else {
			java.util.concurrent.FutureTask<Image> task = this.imageContentWorker.execute();
			return task.get();
		}
	}

	public void clearCachedThumbnail() {
		this.imageContentWorker.clearCachedThumbnail();
	}

	public boolean exceptionThrown() {
		return this.imageContentWorker.exceptionThrown();
	}

	public String getExceptionMessage() {
		return this.imageContentWorker.getException().getMessage();
	}
	// </lg>
}
