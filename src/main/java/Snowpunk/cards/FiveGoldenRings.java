package Snowpunk.cards;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.getSFXFrequency;
import static Snowpunk.SnowpunkMod.makeID;

public class FiveGoldenRings extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(FiveGoldenRings.class.getSimpleName());

    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(FiveGoldenRings.ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static int playSound = 99;

    private static final int COST = 2;

    public FiveGoldenRings() {
        super(ID, COST, TYPE, RARITY, TARGET);
        //magicNumber = baseMagicNumber = 5;
        CardModifierManager.addModifier(this, new GearMod(5));
        exhaust = true;
    }

    @Override
    public float getTitleFontSize() {
        return 19f;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (getGears() > 0) {
            playSound++;
            if (playSound >= getSFXFrequency() && getSFXFrequency() > 0) {
                addToBot(new SFXAction("snowpunk:FIVEGOLDENRINGS"));
                playSound = 0;
                Wiz.atb(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[0], 2, 2));
                if (playSound == 0) {
                    addToBot(new WaitAction(.1f));
                    addToBot(new WaitAction(.1f));
                    addToBot(new WaitAction(.1f));
                }
            } else
                Wiz.atb(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[0], 2, 2));

            Wiz.atb(new AbstractGameAction() {
                @Override
                public void update() {
                    for (AbstractCard card : Wiz.adp().hand.group) {
                        if (getGears() > 0)
                            CardModifierManager.addModifier(card, new PlateMod(getGears(), true));
                        card.superFlash(Color.WHITE.cpy());
                    }
                    isDone = true;
                }
            });
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }
}