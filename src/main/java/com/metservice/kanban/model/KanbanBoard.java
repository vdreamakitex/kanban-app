package com.metservice.kanban.model;

import static java.util.Collections.unmodifiableCollection;
import static org.apache.commons.lang.SystemUtils.LINE_SEPARATOR;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//TODO This class needs more unit tests.

public class KanbanBoard implements Iterable<KanbanBoardRow> {

    private final KanbanBoardColumnList columns;
    private final List<KanbanBoardRow> rows = new ArrayList<KanbanBoardRow>();

    public KanbanBoard(KanbanBoardColumnList columns) {
        this.columns = columns;
    }

    public void merge(KanbanBoard otherBoard) {
        extendLength(otherBoard.rows.size());
        mergeRows(otherBoard);
    }

    private void extendLength(int newLength) {
        for (int i = rows.size(); i < newLength; i++) {
            KanbanBoardRow blankRow = new KanbanBoardRow(columns);
            rows.add(blankRow);
        }
    }

    private void mergeRows(KanbanBoard otherBoard) {
        for (int i = 0; i < otherBoard.rows.size(); i++) {
            KanbanBoardRow destination = rows.get(i);
            KanbanBoardRow source = otherBoard.rows.get(i);
            destination.merge(source);
        }
    }

    public void stack(KanbanBoard otherBoard) {
        for (KanbanBoardRow row : otherBoard) {
            rows.add(row.clone());
        }
    }
    
    public void insert(WorkItem workItem, WorkItem workItemAbove, WorkItem workItemBelow) {
        KanbanBoardRow targetRow = null;
        for (KanbanBoardRow row : this) {
            if (row.canAdd(workItem)) {
                targetRow = row;
                break;
            }
        }
        if (targetRow == null) {
            // no space: add a new row
            targetRow = new KanbanBoardRow(columns);
            rows.add(targetRow);
        }
        targetRow.insert(workItem, workItemAbove, workItemBelow);
    }

    @Override
    public Iterator<KanbanBoardRow> iterator() {
        return unmodifiableCollection(rows).iterator();
    }

    public KanbanCell getCell(int rowIndex, int columnIndex) {
        return rows.get(rowIndex).getCell(columnIndex);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (KanbanBoardRow row : this) {
            builder.append(row);
            builder.append(LINE_SEPARATOR);
        }
        return builder.toString();
    }
}
