package com.company;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        solve_method2(3);
    }

    static void solve_method1(int size)
    {
        Cube2 initial_cube = new Cube2(size);

        ArrayList<HashSet<Cube2>> attempts = new ArrayList<HashSet<Cube2>>();
        attempts.add(new HashSet<Cube2>());
        attempts.get(0).add(initial_cube);



        ArrayList<int[]> possible_moves = initial_cube.get_possible_turns();
        boolean solution_found = false;

        while (!solution_found)
        {
            //get previous solutions
            ArrayList<Cube2> previous_solutions = new ArrayList<Cube2>(attempts.get(attempts.size() - 1));
            HashSet<Cube2> temp_hash = new HashSet<Cube2>();

            for (int i = 0; i < attempts.get(attempts.size() - 1).size(); i ++)
            {
                for (int j = 0; j < possible_moves.size(); j++)
                {
                    //System.out.println(" " + attempts.get(attempts.size() - 1).size()+  possible_moves.size());
                    temp_hash.add(previous_solutions.get(i).turn(possible_moves.get(j)[0], possible_moves.get(j)[1],possible_moves.get(j)[2]));
                }
            }

            //check for solution
            ArrayList<Cube2> current_solutions = new ArrayList<Cube2>(temp_hash);

            for (int i = 0; i < current_solutions.size(); i ++)
            {
                if (current_solutions.get(i).is_solved())
                {
                    System.out.println("Solution found, steps taken: "+ current_solutions.get(i).generation);
                    solution_found = true;
                    break;
                }
            }

            System.out.println("Solution not found, steps taken: "+ current_solutions.get(0).generation + ", solutions at level: " + current_solutions.size());

            attempts.add(temp_hash);

        }
    }

    static void solve_method2(int size)
    {
        Cube2 initial_cube = new Cube2(size);

        HashMap<Square2[][][], Cube2> solutions = new HashMap<Square2[][][], Cube2>();
        solutions.put(initial_cube.cube_squares, initial_cube);
        ArrayList<int[]> possible_moves = initial_cube.get_possible_turns();
        boolean solution_found = false;

        while (!solution_found)
        {
            //get previous solutions
            HashSet<Cube2> temp_hash = new HashSet<Cube2>();
            int solution_size = solutions.size();
            ArrayList<Cube2> temp_solution_array = new ArrayList<Cube2>();

            ArrayList<Cube2> current_solutions = new ArrayList<Cube2>(solutions.values());
            for (int i = 0; i <  current_solutions.size(); i ++)
            {
                for (int j = 0; j < possible_moves.size(); j++)
                {
                    Cube2 temp_cube = current_solutions.get(i).turn(possible_moves.get(j)[0], possible_moves.get(j)[1],possible_moves.get(j)[2]);
                    temp_solution_array.add(temp_cube);
                }
            }

            for (int i = 0; i <  temp_solution_array.size(); i ++) {
                solutions.put(temp_solution_array.get(i).cube_squares ,temp_solution_array.get(i));
            }

            //check for solution


            for (int i = 0; i < current_solutions.size(); i ++)
            {
                if (current_solutions.get(i).is_solved())
                {
                    System.out.println("Solution found, steps taken: "+ current_solutions.get(i).generation);
                    solution_found = true;
                    break;
                }
            }

            System.out.println("Solution not found, steps taken: "+ current_solutions.get(0).generation + ", solutions at level: " + current_solutions.size());

        }
    }
}

