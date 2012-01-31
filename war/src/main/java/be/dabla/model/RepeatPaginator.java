package be.dabla.model;

import java.util.List;

import be.dabla.domain.EJBLocal;

public class RepeatPaginator<T> {
	private static final int DEFAULT_RECORDS_NUMBER = 50;
    private static final int DEFAULT_PAGE_INDEX = 1;

    final EJBLocal<T> ejb;
    int records = DEFAULT_RECORDS_NUMBER;
    int recordsTotal;
    int pageIndex = DEFAULT_PAGE_INDEX;
    int pages;
    List<T> model;

    public RepeatPaginator(final EJBLocal<T> ejb) {
    	this.ejb = ejb;
    	
        updateModel();
    }

    public void updateModel() {
    	this.recordsTotal = ejb.count().intValue();
    	this.model = ejb.findAll(((getPageIndex() - 1) * getRecords()), getRecords());
    	
    	if (records > 0) {
            pages = records <= 0 ? 1 : recordsTotal / records;

            if (recordsTotal % records > 0) {
                pages++;
            }

            if (pages == 0) {
                pages = 1;
            }
        } else {
            records = 1;
            pages = 1;
        }
    }
    
    private boolean isNext() {
    	return this.pageIndex < pages;
    }

    public void next() {
        if (isNext()) {
            this.pageIndex++;
        }

        updateModel();
    }
    
    private boolean isPrev() {
    	return this.pageIndex > 1;
    }

    public void prev() {
        if (isPrev()) {
            this.pageIndex--;
        }

        updateModel();
    }
    
    public void setRecords(int records) {
        this.records = records;
    }

    public int getRecords() {
        return records;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }
    
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getPages() {
        return pages;
    }

    public int getFirst() {
        return (pageIndex * records) - records;
    }

    public List<T> getModel() {
        return model;
    }    
}