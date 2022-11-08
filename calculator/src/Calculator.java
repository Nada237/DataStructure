public class Calculator implements ICalculator{

    @Override
    public int add(int x, int y) {
        return x+y;
    }

    @Override
    public float divide(int x, int y) throws RuntimeException {
        if(y==0) throw new RuntimeException("ERROR");
        return x/y;
    }

}
