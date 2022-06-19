package ora7;

import java.util.Random;

public class genetikus {

    int populationCount = 0;
    int crossoverCount = 0;
    float mutationProbability = 0.1f;

    public genetikus(int count, int populationCount, int crossoverCount, float mutationProbability) {
        this.populationCount = populationCount;
        this.crossoverCount = crossoverCount;
        this.mutationProbability = mutationProbability;

        Random r = new Random();
        int randomNumer = r.nextInt((100 - 0) + 1);

        for (var i = 0; i <= populationCount; ++i) {
           createRandomEntity();
       }

        for (var i = 0; i <= populationCount; ++i) {
            //doCrossover(Entity e1, Entity e2);
        }

        //if (randomNumer <= (int)(mutationProbability * 10) ) mutateEntity(Entity e);

    }



    void createRandomEntity() {

    }

    void doCrossover(Entity e1, Entity e2) {

    }

    void mutateEntity(Entity e) {

    }
}
