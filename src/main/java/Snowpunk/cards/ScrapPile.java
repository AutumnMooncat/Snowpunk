package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.SoulBarrageAction;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class ScrapPile extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ScrapPile.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1, DMG = 6, BLOCK = 6;

    public ScrapPile() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DMG;
        block = baseBlock = BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        //dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.atb(new SoulBarrageAction(Color.GOLD, this));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardModifierManager.addModifier(this, new PlateMod(1)));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new PlateMod(2)));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new PlateMod(3)));
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }
}