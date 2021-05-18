package ee.ttu.java.cookie;


public class CookieGame {

    public static final int TWENTY = 20;

    public static final int HUNDRED = 100;
 
    public static final int TWOHUNDRED = 200;

    public static final int FIVEK = 5000;

    public static final int THOUSAND = 1000;

    private int cursorPrice = TWENTY;

    private int cursorPriceIncrease = TWENTY;

    private int cursorCount = 1;
 
    private int cookies = 0;

    private int clickerPrice = HUNDRED;

    private int clickerPriceIncrease = TWOHUNDRED;

    private int clickerInterval = FIVEK;

    private int clickerIntervalDecrease = HUNDRED;

    private int clickerIntervalMin = THOUSAND;

    private int clickerCount = 0;

    public CookieGame() {
    }

    public CookieGame(int cursorPrice, int cursorPriceIncrease) {
        this.cursorPrice = cursorPrice;
        this.cursorPriceIncrease = cursorPriceIncrease;
    }

     
    public CookieGame(int cursorPrice, int cursorPriceIncrease, int clickerPrice, int clickerPriceIncrease,
                      int clickerInterval, int clickerIntervalDecrease, int clickerIntervalMin) {
        this.cursorPrice = cursorPrice;
        this.cursorPriceIncrease = cursorPriceIncrease;
        this.clickerPrice = clickerPrice;
        this.clickerInterval = clickerInterval;
        this.clickerPriceIncrease = clickerPriceIncrease;
        this.clickerIntervalDecrease = clickerIntervalDecrease;
        this.clickerIntervalMin = clickerIntervalMin;
    }


    public int getCursorPrice() {
        return this.cursorPrice;
    }

    public int getCookies() {
        return this.cookies;
    }


    public int getCursorCount() {
        return this.cursorCount;
    }


    public int getClickerCount() {
        return this.clickerCount;
    }

   
    public int getClickerPrice() {
        return this.clickerPrice;
    }

    public int getClickerInterval() {
        return this.clickerInterval;
    }


    public void clickerAction() {
        if (this.clickerCount > 0) {
            this.cookies += this.cursorCount;
        }
    }


    public void click() {
        this.cookies += this.cursorCount;
    }

    public void buyCursor() {
        if (canBuyCursor()) {
            this.cookies -= this.cursorPrice;
            this.cursorCount += 1;
            this.cursorPrice += this.cursorPriceIncrease;
        }
    }

    public int getClickerIntervalMin() {
        return this.clickerIntervalMin;
    }


    public void buyClicker() {
        if (canBuyClicker()) {
            this.cookies -= this.clickerPrice;
            this.clickerCount += 1;
            this.clickerPrice += this.clickerPriceIncrease;
            if (this.clickerCount != 1) {
                this.clickerInterval -= this.clickerIntervalDecrease;
                if (this.clickerInterval < this.clickerIntervalMin) {
                    this.clickerInterval = this.clickerIntervalMin;
                }
            }
        }
    }

    public boolean canBuyCursor() {
        return this.cookies >= this.cursorPrice && this.cookies > 0 && this.cursorPrice > 0;
    }

    public boolean canBuyClicker() {
        return this.cookies >= this.clickerPrice && this.cookies > 0 && this.clickerPrice > 0;
    }
}
