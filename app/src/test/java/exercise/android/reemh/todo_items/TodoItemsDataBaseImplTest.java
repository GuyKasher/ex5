package exercise.android.reemh.todo_items;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class TodoItemsDataBaseImplTest {
  @Test
  public void when_addingTodoItem_then_callingListShouldHaveThisItem(){
    // setup
    TodoItemsDataBaseImpl dbUnderTest = new TodoItemsDataBaseImpl();
    Assert.assertEquals(0, dbUnderTest.getCurrentItems().size());

    // test
    dbUnderTest.addNewInProgressItem("do shopping");

    // verify
    Assert.assertEquals(1, dbUnderTest.getCurrentItems().size());
  }
//1
  @Test
  public void when_addingTodoItemThenDelete_then_callingListShouldNotHaveThisItem(){
    // setup
    TodoItemsDataBaseImpl dbUnderTest = new TodoItemsDataBaseImpl();
    Assert.assertEquals(0, dbUnderTest.getCurrentItems().size());

    // test
    dbUnderTest.addNewInProgressItem("do shopping");
    dbUnderTest.deleteItem(dbUnderTest.getCurrentItems().get(0));
    // verify
    Assert.assertEquals(0, dbUnderTest.getCurrentItems().size());
  }
  //2
  @Test
  public void when_makeToDOItemOnProgress_then_itemStatusChanged(){
    // setup
    TodoItemsDataBaseImpl dbUnderTest = new TodoItemsDataBaseImpl();
    Assert.assertEquals(0, dbUnderTest.getCurrentItems().size());

    // test
    dbUnderTest.addNewInProgressItem("do shopping");
    dbUnderTest.markItemInProgress(dbUnderTest.getCurrentItems().get(0));
    // verify
    Assert.assertEquals(1, dbUnderTest.getCurrentItems().size());
    Assert.assertEquals(Status.INPROGRASS, dbUnderTest.getCurrentItems().get(0).status);

  }
  //3
  @Test
  public void when_makeToDOItemDone_then_itemStatusChanged(){
    // setup
    TodoItemsDataBaseImpl dbUnderTest = new TodoItemsDataBaseImpl();
    Assert.assertEquals(0, dbUnderTest.getCurrentItems().size());

    // test
    dbUnderTest.addNewInProgressItem("do shopping");
    dbUnderTest.markItemDone(dbUnderTest.getCurrentItems().get(0));
    // verify
    Assert.assertEquals(1, dbUnderTest.getCurrentItems().size());
    Assert.assertEquals(Status.DONE, dbUnderTest.getCurrentItems().get(0).status);
  }
  //4
  @Test
  public void when_sort_then_itemArrayIsSort(){
    // setup
    TodoItemsDataBaseImpl dbUnderTest = new TodoItemsDataBaseImpl();
    Assert.assertEquals(0, dbUnderTest.getCurrentItems().size());

    // test
    dbUnderTest.addNewInProgressItem("do shopping");
    dbUnderTest.addNewInProgressItem("playing");

    dbUnderTest.markItemInProgress(dbUnderTest.getCurrentItems().get(1));
    dbUnderTest.sortItems();
    // verify
    Assert.assertEquals(2, dbUnderTest.getCurrentItems().size());
    Assert.assertEquals("playing", dbUnderTest.getCurrentItems().get(1).description);
  }

  //5
  @Test
  public void when_addThreeItemsThenCheckIdsAfterSort_then_IdIsCorrect(){
    // setup
    TodoItemsDataBaseImpl dbUnderTest = new TodoItemsDataBaseImpl();
    Assert.assertEquals(0, dbUnderTest.getCurrentItems().size());

    // test
    dbUnderTest.addNewInProgressItem("do shopping");
    dbUnderTest.addNewInProgressItem("playing");
    dbUnderTest.addNewInProgressItem("learning");


    dbUnderTest.markItemInProgress(dbUnderTest.getCurrentItems().get(1));
    dbUnderTest.sortItems();
    // verify
    Assert.assertEquals(3, dbUnderTest.getCurrentItems().size());
    Assert.assertEquals("playing", dbUnderTest.getCurrentItems().get(0).description);
  }
  //6
  @Test
  public void when_addThreeItemsThenCheckIdsMakeOneDoneAndOneOnProgress_then_IdIsCorrect(){
    // setup
    TodoItemsDataBaseImpl dbUnderTest = new TodoItemsDataBaseImpl();
    Assert.assertEquals(0, dbUnderTest.getCurrentItems().size());

    // test
    dbUnderTest.addNewInProgressItem("do shopping");
    dbUnderTest.addNewInProgressItem("playing");
    dbUnderTest.addNewInProgressItem("learning");


    dbUnderTest.markItemInProgress(dbUnderTest.getCurrentItems().get(1));
    dbUnderTest.markItemDone(dbUnderTest.getCurrentItems().get(2));
    dbUnderTest.sortItems();
    // verify
    Assert.assertEquals(3, dbUnderTest.getCurrentItems().size());
    Assert.assertEquals("playing", dbUnderTest.getCurrentItems().get(0).description);
    Assert.assertEquals("learning", dbUnderTest.getCurrentItems().get(1).description);

  }
  //7
  @Test
  public void when_addThreeItemsThenCheckChangeStatus_then_statusIsOK(){
    // setup
    TodoItemsDataBaseImpl dbUnderTest = new TodoItemsDataBaseImpl();
    Assert.assertEquals(0, dbUnderTest.getCurrentItems().size());

    // test
    dbUnderTest.addNewInProgressItem("do shopping");
    dbUnderTest.addNewInProgressItem("playing");
    dbUnderTest.addNewInProgressItem("learning");


    dbUnderTest.markItemInProgress(dbUnderTest.getCurrentItems().get(1));
    dbUnderTest.markItemDone(dbUnderTest.getCurrentItems().get(2));

    // verify
    Assert.assertEquals(3, dbUnderTest.getCurrentItems().size());
    Assert.assertEquals(Status.NOTDONE, dbUnderTest.getCurrentItems().get(1).status);
    Assert.assertEquals(Status.DONE, dbUnderTest.getCurrentItems().get(2).status);
    Assert.assertEquals(Status.INPROGRASS, dbUnderTest.getCurrentItems().get(0).status);


  }

  //8
  @Test
  public void when_addItemDeleteHimAndAddAgain_then_justOneItemExist(){
    // setup
    TodoItemsDataBaseImpl dbUnderTest = new TodoItemsDataBaseImpl();
    Assert.assertEquals(0, dbUnderTest.getCurrentItems().size());

    // test
    dbUnderTest.addNewInProgressItem("do shopping");
    dbUnderTest.deleteItem(dbUnderTest.getCurrentItems().get(0));
    dbUnderTest.addNewInProgressItem("learning");




    // verify
    Assert.assertEquals(1, dbUnderTest.getCurrentItems().size());



  }
  //9
  @Test
  public void when_SortByIdSameStatusItems_then_statusIsOK(){
    // setup
    TodoItemsDataBaseImpl dbUnderTest = new TodoItemsDataBaseImpl();
    Assert.assertEquals(0, dbUnderTest.getCurrentItems().size());

    // test
    dbUnderTest.addNewInProgressItem("do shopping");
    dbUnderTest.addNewInProgressItem("playing");
    dbUnderTest.addNewInProgressItem("learning");

    dbUnderTest.markItemDone(dbUnderTest.getCurrentItems().get(1));
    dbUnderTest.markItemDone(dbUnderTest.getCurrentItems().get(2));
    dbUnderTest.markItemDone(dbUnderTest.getCurrentItems().get(0));

    // verify
    Assert.assertEquals(3, dbUnderTest.getCurrentItems().size());
    Assert.assertEquals("do shopping", dbUnderTest.getCurrentItems().get(0).description);
    Assert.assertEquals("playing", dbUnderTest.getCurrentItems().get(2).description);
    Assert.assertEquals("learning", dbUnderTest.getCurrentItems().get(1).description);


  }
//10
  @Test
  public void when_changeItemStatus4Times_then_justOneItemExist(){
    // setup
    TodoItemsDataBaseImpl dbUnderTest = new TodoItemsDataBaseImpl();
    Assert.assertEquals(0, dbUnderTest.getCurrentItems().size());

    // test
    dbUnderTest.addNewInProgressItem("do shopping");
    dbUnderTest.markItemInProgress(dbUnderTest.getCurrentItems().get(0));
    Assert.assertEquals(Status.INPROGRASS, dbUnderTest.getCurrentItems().get(0).status);

    dbUnderTest.markItemDone(dbUnderTest.getCurrentItems().get(0));
    Assert.assertEquals(Status.DONE, dbUnderTest.getCurrentItems().get(0).status);

    dbUnderTest.markItemInProgress(dbUnderTest.getCurrentItems().get(0));
    Assert.assertEquals(Status.INPROGRASS, dbUnderTest.getCurrentItems().get(0).status);

    dbUnderTest.markItemDone(dbUnderTest.getCurrentItems().get(0));
    Assert.assertEquals(Status.DONE, dbUnderTest.getCurrentItems().get(0).status);





    // verify
    Assert.assertEquals(1, dbUnderTest.getCurrentItems().size());



  }


  // TODO: add at least 10 more tests to verify correct behavior of your implementation of `TodoItemsDataBaseImpl` class
}