package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.CarolingPower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class Caroling extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Caroling.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;
    public static final ArrayList<AbstractCard> Carols = new ArrayList<AbstractCard>() {
        {
            add(new JingleBells());
            add(new SilverBells());
            add(new SleighRide());
            add(new BetterWatchOut());
            add(new ChestnutsRoasting());
            add(new DeckTheHalls());
            add(new FavoriteThings());
            add(new HeatMiser());
            add(new ItsColdOutside());
            add(new LetItSnow());
            add(new RockinAround());
            //add(new SnowMiser());
            add(new FiveGoldenRings());
            add(new TheSnowman());
        }
    };

    public Caroling() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 4;
        secondMagic = baseSecondMagic = 1;
        cardToPreview.addAll(Carols);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new CarolingPower(p, 4));
        for (int i = 0; i < secondMagic; i++) {
            Wiz.atb(new AbstractGameAction() {
                @Override
                public void update() {
                    Wiz.atb(new AbstractGameAction() {

                        @Override
                        public void update() {
                            AbstractCard card = Caroling.Carols.get(AbstractDungeon.cardRandomRng.random(Caroling.Carols.size())).makeStatEquivalentCopy();
                            Wiz.atb(new MakeTempCardInHandAction(card));
                            isDone = true;
                        }
                    });
                    isDone = true;
                }
            });
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(2));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> upgradeSecondMagic(1));
    }
}