package Snowpunk.cards;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
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

import static Snowpunk.SnowpunkMod.makeID;

public class FiveGoldenRings extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(FiveGoldenRings.class.getSimpleName());

    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(FiveGoldenRings.ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static ArrayList<TooltipInfo> Tooltip;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (Tooltip == null) {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.GEAR), BaseMod.getKeywordDescription(KeywordManager.GEAR)));
        }
        return Tooltip;
    }

    private static final int COST = 1;

    public FiveGoldenRings() {
        super(ID, COST, TYPE, RARITY, TARGET);
        exhaust = true;
    }

    @Override
    public float getTitleFontSize() {
        return 19f;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("snowpunk:FIVEGOLDENRINGS"));
        Wiz.atb(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[0], 2, 2));
        addToBot(new WaitAction(.1f));
        addToBot(new WaitAction(.1f));
        addToBot(new WaitAction(.1f));
        addToBot(new WaitAction(.1f));
        addToBot(new WaitAction(.1f));
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard card : Wiz.adp().hand.group) {
                    CardModifierManager.addModifier(card, new GearMod(5));
                    card.superFlash(Color.WHITE.cpy());
                }
                isDone = true;
            }
        });
        addToBot(new WaitAction(.1f));
        addToBot(new WaitAction(.1f));
        addToBot(new WaitAction(.1f));
        addToBot(new WaitAction(.1f));
        addToBot(new WaitAction(.1f));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(0));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> {
            exhaust = false;
            uDesc();
        });
    }
}