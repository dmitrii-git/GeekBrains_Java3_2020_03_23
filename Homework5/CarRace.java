import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class CarRace {
        public static final int CARS_COUNT = 4;
        public static CountDownLatch startLine = new CountDownLatch(CARS_COUNT);
        public static Semaphore tunelSemaphore = new Semaphore(Math.round(CARS_COUNT / 2));
        public static int start = 0;
        public static int finish = 0;
        public static int stageLenght = 0;
        public static int raceWinner = 0;



        public static void main(String[] args) throws InterruptedException {
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
            Race race = new Race(new Road(60), new Tunnel(), new Road(40));
            Car[] cars = new Car[CARS_COUNT];
            for (int i = 0; i < cars.length; i++) {
                cars[i] = new Car(race, 20 + (int) (Math.random() * 10));

            }
            for (int i = 0; i < cars.length; i++) {
                new Thread(cars[i]).start();
            }
            if (startLine.getCount() == 0L) System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            //System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        }
    }
    class Car implements Runnable {
        private static int CARS_COUNT;
        static {
            CARS_COUNT = 0;
        }
        private Race race;
        private int speed;
        private String name;
        public String getName() {
            return name;
        }
        public int getSpeed() {
            return speed;
        }
        public Car(Race race, int speed) {
            this.race = race;
            this.speed = speed;
            CARS_COUNT++;
            this.name = "Участник #" + CARS_COUNT;
        }
        @Override
        public void run() {
            try {
                System.out.println(this.name + " готовится");
                Thread.sleep(500 + (int)(Math.random() * 800));
                CarRace.startLine.countDown();
                //System.out.println(CarRace.startLine.getCount());
                System.out.println(this.name + " готов");
                CarRace.startLine.await();
                CarRace.start++;
                if (CarRace.start == CARS_COUNT) System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
        }
    }
    abstract class Stage {
        protected int length;
        protected String description;
        public String getDescription() {
            return description;
        }
        public abstract void go(Car c);
    }
    class Road extends Stage {
        public Road(int length) {
            this.length = length;
            this.description = "Дорога " + length + " метров";
        }
        @Override
        public void go(Car c) {
            try {
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
                System.out.println(c.getName() + " закончил этап: " + description);
                CarRace.finish++;
                Finish.allFinish();
                Finish.winner(c.getName());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    class Tunnel extends Stage {
        public Tunnel() {
            this.length = 80;
            this.description = "Тоннель " + length + " метров";
        }
        @Override
        public void go(Car c) {
            try {
                try {
                    System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                    CarRace.tunelSemaphore.acquire();
                    System.out.println(c.getName() + " начал этап: " + description);
                    Thread.sleep(length / c.getSpeed() * 1000);
                    CarRace.tunelSemaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(c.getName() + " закончил этап: " + description);
                    CarRace.finish++;
                    Finish.allFinish();
                    Finish.winner(c.getName());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    class Race {

        private ArrayList<Stage> stages;
        public ArrayList<Stage> getStages() { return stages; }
        public Race(Stage... stages) {
            this.stages = new ArrayList<>(Arrays.asList(stages));
            CarRace.stageLenght = stages.length;
        }

    }

    class Finish {
        public static void allFinish() {
            if(CarRace.finish == (CarRace.stageLenght*CarRace.CARS_COUNT)){
                 System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
            }
        }
        public static void winner(String racer) {
            if(CarRace.finish == CarRace.stageLenght && CarRace.raceWinner == 0){
                 System.out.println("Участник " + racer + " выиграл гонку!!!!");
            }
        }

    }







