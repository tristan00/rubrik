package com.company;
import java.util.*;

public class Cube2 {

    //3d array with corners blanck
    Square2[][][] cube_squares=  new Square2[1][1][1];
    ArrayList<String> colors = new ArrayList<String>();
    int size = 0;
    int generation = 0;

    HashSet<Square2[][][]> alternate_solutions = new HashSet<Square2[][][]>();

    public Cube2(int size, Square2[][][] precedant_copy, int generation)
    {
        this.generation = generation;
        cube_squares =  precedant_copy;

        this.size = size;

        colors.add("None");
        colors.add("Yellow");
        colors.add("White");
        colors.add("Orange");
        colors.add("Red");
        colors.add("Green");
        colors.add("Blue");
    }

    public Cube2(int size) {
        this.size = size;
        cube_squares =  new Square2[size+2][size+2][size+2];

        colors.add("None");
        colors.add("Yellow");
        colors.add("White");
        colors.add("Orange");
        colors.add("Red");
        colors.add("Green");
        colors.add("Blue");

        for (int i = 0; i < size + 2; i++)
        {
            for (int j = 0; j < size + 2; j++) {
                for (int k = 0; k < size + 2; k++)
                {
                    String temp_color= colors.get(0);
                    boolean non_null = false;

                    if (k == 0 && ( i > 0 && i < size + 1) && ( j > 0 && j < size + 1))
                    {
                        temp_color = colors.get(1);
                        non_null = true;
                    }
                    if (k == size + 1 && ( i > 0 && i < size + 1) && ( j > 0 && j < size + 1))
                    {
                        temp_color = colors.get(2);
                        non_null = true;
                    }
                    if (j == 0 && ( k > 0 && k < size + 1) && ( i > 0 && i < size + 1))
                    {
                        temp_color = colors.get(3);
                        non_null = true;
                    }
                    if (j == size + 1 && ( k > 0 && k < size + 1) && ( i > 0 && i < size + 1))
                    {
                        temp_color = colors.get(4);
                        non_null = true;
                    }
                    if (i == 0 && ( k > 0 && k < size + 1) && ( j > 0 && j < size + 1))
                    {
                        temp_color = colors.get(5);
                        non_null = true;
                    }
                    if (i == size + 1 && ( k > 0 && k < size + 1) && ( j > 0 && j < size + 1))
                    {
                        temp_color = colors.get(6);
                        non_null = true;
                    }

                    if (non_null)
                    {
                        cube_squares[i][j][k] = new Square2(temp_color);
                    }
                }
            }
        }

        shuffle(1000);


    }

    public boolean is_solved()
    {


        ArrayList<Set<String>> sides = new ArrayList<Set<String>>();
        for (int i = 1; i <colors.size(); i++)
        {
            sides.add(new HashSet<String>());
        }


        for (int i = 0; i < size + 2; i++)
        {
            for (int j = 0; j < size + 2; j++)
            {
                for (int k = 0; k < size + 2; k++)
                {


                    try {
                        if (k == 0 && ( i > 0 && i < size + 1) && ( j > 0 && j < size + 1)) {
                            sides.get(0).add(cube_squares[i][j][k].color);
                        }
                        if (k == size + 1 && ( i > 0 && i < size + 1) && ( j > 0 && j < size + 1)) {
                            sides.get(1).add(cube_squares[i][j][k].color);
                        }
                        if (j == 0 && ( k > 0 && k < size + 1) && ( i > 0 && i < size + 1)) {
                            sides.get(2).add(cube_squares[i][j][k].color);
                        }
                        if (j == size + 1 && ( k > 0 && k < size + 1) && ( i > 0 && i < size + 1)) {
                            sides.get(3).add(cube_squares[i][j][k].color);
                        }
                        if (i == 0 && ( k > 0 && k < size + 1) && ( j > 0 && j < size + 1)) {
                            sides.get(4).add(cube_squares[i][j][k].color);
                        }
                        if (i == size + 1 && ( k > 0 && k < size + 1) && ( j > 0 && j < size + 1)) {
                            sides.get(5).add(cube_squares[i][j][k].color);
                        }
                    }
                    catch(NullPointerException E)
                    {

                    }
                }
            }
        }

        for(int i = 1; i <size; i++)
        {
            if (sides.get(i).size() > 1)
            {
                return false;
            }
        }

        return true;

    }

    public ArrayList<int[]> get_possible_turns(){
        //lookign from south, turn orientation 0 is north/south, 1 is east west and 2 is the one perpendicular to players position.

        ArrayList<int[]> turns = new ArrayList<int[]>();

        for (int i = 0 ; i < 3; i++)// 3 possible turns
        {
            for (int j  = 0; j < size; j++)
            {
                turns.add(new int[]{i,-1, j});
                turns.add(new int[]{i,1, j});
            }
        }
        return turns;
    }

    public Cube2 turn(int orientation, int direction, int depth_within_side)
    {
        //turn orientation 1 is round k axis, 2 is i axis and 3 is j axis

        int adjusted_depth_within_side = depth_within_side + 1;
        Square2[][][] temp_cube = cube_squares.clone();
        Square2[][] temp_data = new  Square2[size + 2][size + 2];
        Square2[][] temp_data2 = new  Square2[size + 2][size + 2];// for the side

        if (orientation == 1)
        {
            for (int i = 0; i < size + 2; i ++)
            {
                for (int j = 0; j < size + 2; j ++)
                {
                    temp_data[i][j] = cube_squares[i][j][adjusted_depth_within_side];

                    if (depth_within_side == 0)
                    {
                        temp_data2[i][j] = cube_squares[i][j][0];
                    }
                    else
                        {
                            if (depth_within_side == size - 1)
                            {
                                temp_data2[i][j] = cube_squares[i][j][size + 1];
                            }
                        }
                }

            }

            temp_data = rotate(temp_data, direction);
            temp_data2 = rotate(temp_data2, direction);

            for (int i = 0; i < size + 2; i ++)
            {
                for (int j = 0; j < size + 2; j ++)
                {
                    temp_cube[i][j][adjusted_depth_within_side] = temp_data[i][j];
                    if (depth_within_side == 0)
                    {
                        temp_cube[i][j][0] = temp_data2[i][j];
                    }
                    if (depth_within_side == size-1)
                    {
                        temp_cube[i][j][size-1] = temp_data2[i][j];
                    }
                }
            }

        }

        if (orientation == 2)
        {
            for (int k = 0; k < size + 2; k ++)
            {
                for (int j = 0; j < size + 2; j ++)
                {
                    temp_data[j][k] = cube_squares[adjusted_depth_within_side][j][k];

                    if (depth_within_side == 0)
                    {
                        temp_data2[j][k] = cube_squares[0][j][k];
                    }
                    else
                    {
                        if (depth_within_side == size - 1)
                        {
                            temp_data2[j][k] = cube_squares[size + 1][j][k];
                        }
                    }
                }

            }

            temp_data = rotate(temp_data, direction);
            temp_data2 = rotate(temp_data2, direction);

            for (int k = 0; k < size + 2; k ++)
            {
                for (int j = 0; j < size + 2; j ++)
                {
                    temp_cube[adjusted_depth_within_side][j][k] = temp_data[j][k];
                    if (depth_within_side == 0)
                    {
                        temp_cube[0][j][k] = temp_data2[j][k];
                    }
                    if (depth_within_side == size-1)
                    {
                        temp_cube[size-1][j][k] = temp_data2[j][k];
                    }
                }
            }

        }

        if (orientation == 3)
        {
            for (int i = 0; i < size + 2; i ++)
            {
                for (int k = 0; k < size + 2; k ++)
                {
                    temp_data[i][k] = cube_squares[i][adjusted_depth_within_side][k];

                    if (depth_within_side == 0)
                    {
                        temp_data2[i][k] = cube_squares[i][0][k];
                    }
                    else
                    {
                        if (depth_within_side == size - 1)
                        {
                            temp_data2[i][k] = cube_squares[i][size + 1][k];
                        }
                    }
                }

            }

            temp_data = rotate(temp_data, direction);
            temp_data2 = rotate(temp_data2, direction);

            for (int i = 0; i < size + 2; i ++)
            {
                for (int k = 0; k < size + 2; k ++)
                {
                    temp_cube[i][adjusted_depth_within_side][k] = temp_data[i][k];
                    if (depth_within_side == 0)
                    {
                        temp_cube[i][0][k] = temp_data2[i][k];
                    }
                    if (depth_within_side == size-1)
                    {
                        temp_cube[i][size-1][k] = temp_data2[i][k];
                    }
                }
            }

        }

        return new Cube2(size, temp_cube, generation + 1);
    }

    public Square2[][] rotate(Square2[][] slice_to_rotate, int direction)
    {
        //transpose + reverse to rotate 90 degrees

        Square2[][] result = new Square2[size + 2][size + 2];
        for (int i = 0; i < size + 2; ++i) {
            for (int j = 0; j < size + 2; ++j) {
                if (direction == -1)
                {
                    result[i][j] = slice_to_rotate[size + 2 - j - 1][ i];
                }
                if (direction == 1)
                {
                    result[i][j] = slice_to_rotate[j][size + 2 - i - 1];
                }
            }
        }
        return result;
    }

    public void shuffle(int moves)
    {
        for (int i = 0; i < moves; i++)
        {
            ArrayList<int[]> possible_moves = get_possible_turns();
            Collections.shuffle(possible_moves);

            turn(possible_moves.get(0)[0], possible_moves.get(0)[1],possible_moves.get(0)[2]);
        }
    }
}

class Square2{
    String color = "";

    public Square2(String color)
    {
        this.color = color;
    }
}

class CubeHashSet extends HashMap
{


}



