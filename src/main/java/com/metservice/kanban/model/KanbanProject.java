package com.metservice.kanban.model;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.joda.time.LocalDate;

/**
 * The interface for a KanbanProject representation.
 * 
 * @author Janella Espinas, Chris Cooper
 */
public interface KanbanProject {

    void deleteWorkItem(int i);

    void save() throws IOException;

    void advance(int id, LocalDate date);

    void stop(int id);

    int addWorkItem(int parentId, WorkItemType type, String name, int averageCaseEstimate, int worstCaseEstimate,
                    int importance, String notes, String color, boolean excluded, String workStreams,
                    LocalDate backlogDate);

    void move(int id, int targetId, boolean after);

    void reorder(Integer id, Integer[] newIdList);

    WorkItemTree getWorkItemTree();

    WorkItem getWorkItemById(int id);

    KanbanBoardColumnList getColumns(BoardIdentifier boardType);

    KanbanBoardColumnList getWallColumns();

    void reparentWorkItem(int id, int newParentId);

    KanbanBacklog getBacklog(String workStream);

    KanbanBoard getCompleted(String workStream);

    KanbanBoard getBoard(BoardIdentifier boardType, String workStream);

    WorkItemTypeCollection getWorkItemTypes();

    //    String getJournalText();

    //    void writeJournalText(String journalText);
    List<KanbanJournalItem> getJournal();

    void addJournalItem(KanbanJournalItem journalItem);

    void updateJournalItem(KanbanJournalItem journalItem);

    void deleteJournalItem(int itemId);

    Set<String> getWorkStreams();

    LocalDate getStartDate();

    LocalDate getProjectedEndDate(LocalDate startDate, LocalDate endDate);

    WorkItemType getChildType(WorkItemType parentType);
}
