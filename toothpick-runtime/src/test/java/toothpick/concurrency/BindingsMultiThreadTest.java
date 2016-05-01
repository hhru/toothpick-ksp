package toothpick.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import toothpick.ToothPick;
import toothpick.concurrency.threads.AddSameScopeThread;
import toothpick.concurrency.threads.AddScopeToListThread;
import toothpick.concurrency.threads.GetInstanceThread;
import toothpick.concurrency.threads.InstallBindingThread;
import toothpick.concurrency.threads.RemoveSameScopeThread;
import toothpick.concurrency.threads.RemoveScopeFromListThread;
import toothpick.concurrency.threads.ScopeToStringThread;
import toothpick.concurrency.threads.TestableThread;
import toothpick.concurrency.utils.ClassCreator;
import toothpick.concurrency.utils.ThreadTestUtil;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static toothpick.concurrency.utils.ThreadTestUtil.STANDARD_THREAD_COUNT;

public class BindingsMultiThreadTest {

  static final String ROOT_SCOPE = "ROOT_SCOPE";
  final List<Object> scopeNames = new CopyOnWriteArrayList<>();
  private static ClassCreator classCreator = new ClassCreator();

  @Before
  public void setUp() throws Exception {
    ToothPick.openScope(ROOT_SCOPE);
    scopeNames.clear();
  }

  @After
  public void tearDown() throws Exception {
    ToothPick.reset();
    ThreadTestUtil.shutdown();
  }

  @Test
  public void concurrentBindingInstall_shouldNotCrash() throws InterruptedException {
    //GIVEN
    final int addNodeThreadCount = STANDARD_THREAD_COUNT;
    List<TestableThread> threadList = new ArrayList<>();

    //WHEN
    for (int indexThread = 0; indexThread < addNodeThreadCount; indexThread++) {
      InstallBindingThread runnable = new InstallBindingThread(classCreator, ROOT_SCOPE);
      threadList.add(runnable);
      ThreadTestUtil.submit(runnable);
    }

    //THEN
    //we simply should not have crashed when all threads are done
    ThreadTestUtil.shutdown();
    for (TestableThread thread : threadList) {
      assertTrue(String.format("test of thread %s failed", thread.getName()), thread.isSuccessful());
    }
  }

  @Test
  public void concurrentBindingInstallAndToString_shouldNotCrash() throws InterruptedException {
    //GIVEN
    final int addNodeThreadCount = STANDARD_THREAD_COUNT;
    List<TestableThread> threadList = new ArrayList<>();
    Random random = new Random();

    //WHEN
    for (int indexThread = 0; indexThread < addNodeThreadCount; indexThread++) {
      TestableThread runnable;
      if(random.nextInt(100) < 20) {
        runnable = new InstallBindingThread(classCreator, ROOT_SCOPE);
      } else {
        runnable = new ScopeToStringThread(ROOT_SCOPE);
      }
      threadList.add(runnable);
      ThreadTestUtil.submit(runnable);
    }

    //THEN
    //we simply should not have crashed when all threads are done
    ThreadTestUtil.shutdown();
    for (TestableThread thread : threadList) {
      assertTrue(String.format("test of thread %s failed", thread.getName()), thread.isSuccessful());
    }
  }

  @Test
  public void concurrentBindingInstallAndGetInstance_shouldNotCrash() throws InterruptedException {
    //GIVEN
    final int addNodeThreadCount = STANDARD_THREAD_COUNT;
    List<TestableThread> threadList = new ArrayList<>();
    Random random = new Random();

    //WHEN
    for (int indexThread = 0; indexThread < addNodeThreadCount; indexThread++) {
      TestableThread runnable;
      if(random.nextInt(100) < 20) {
        runnable = new InstallBindingThread(classCreator, ROOT_SCOPE);
      } else {
        runnable = new GetInstanceThread(ROOT_SCOPE, classCreator.allClasses[random.nextInt(classCreator.allClasses.length)]);
      }
      threadList.add(runnable);
      ThreadTestUtil.submit(runnable);
    }

    //THEN
    //we simply should not have crashed when all threads are done
    ThreadTestUtil.shutdown();
    for (TestableThread thread : threadList) {
      assertTrue(String.format("test of thread %s failed", thread.getName()), thread.isSuccessful());
    }
  }

}
