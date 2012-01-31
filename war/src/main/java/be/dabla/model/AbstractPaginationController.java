package be.dabla.model;

import be.dabla.AbstractInitialContextProvider;

public abstract class AbstractPaginationController<T> extends AbstractInitialContextProvider {
	final RepeatPaginator<T> paginator;
	
	protected AbstractPaginationController(final RepeatPaginator<T> paginator) {
		this.paginator = paginator;
    }

    public RepeatPaginator<T> getPaginator() {
    	return paginator;
    }
}