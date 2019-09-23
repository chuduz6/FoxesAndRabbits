import java.util.List;
import java.util.Random;
import java.util.ArrayList;

/**
 * Animal is an abstract superclass for animals.
 * It provides features common to all animals,
 * such as the location and age.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2006.03.30
 */
public abstract class Animal
{
    // The animal's age.
    public int age;
    // Whether the animal is alive or not.
    public boolean alive;
    // The animal's position
    public Location location;
    public Field field;
    

    
    // A shared random number generator to control breeding.
    private static final Random rand = new Random();

    /**
     * Create a new animal with age zero (a new born).
     */
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        age = 0;
    }
    
    abstract public void act(Field currentField, Field updatedField, List<Animal> newAnimals);
    
    /**
     * Make this animal act - that is: make it do whatever
     * it wants/needs to do.
     * @param currentField The field currently occupied.
     * @param updatedField The field to transfer to.
     * @param newAnimals A list to add newly born animals to.
     */
    
    
    /**
     * Check whether the animal is alive or not.
     * @return True if the animal is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Tell the animal that it's dead now :(
     */
    public void setDead()
    {
        alive = false;
        
            location = null;
            field = null;
        
    }
    
    /**
     * Return the animal's age.
     * @return The animal's age.
     */
    public int getAge()
    {
        return age;
    }

    /**
     * Set the animal's age.
     * @param age The animal's age.
     */
    public void setAge(int age)
    {
        this.age = age;
    }
    
    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Set the animal's location.
     * @param row The vertical coordinate of the location.
     * @param col The horizontal coordinate of the location.
     */
    public void setLocation(int row, int col)
    {
        this.location = new Location(row, col);
    }
    
    

    /**
     * Set the animal's location.
     * @param location The animal's location.
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }
    
    public boolean canBreed()
    {
        return age >= getBreedingAge();
    }
    
    abstract public int getBreedingAge();
    
    /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
    public void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            alive = false;
        }
    }
    
    abstract public int getMaxAge();
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    public int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
    
    abstract public double getBreedingProbability();
    abstract public int getMaxLitterSize();
    
    public void giveBirth (Field currentField, Field updatedField, List<Animal> newAnimals)
    {
        if(isAlive()) {
            int births = breed();
            for(int b = 0; b < births; b++) {
                Animal newOne = birthAnimal();
                newAnimals.add(newOne);
                newOne.setLocation(
                        updatedField.randomAdjacentLocation(getLocation()));
                updatedField.place(newOne);
            }
            

        }
    }
    
    abstract public Animal birthAnimal();
}
