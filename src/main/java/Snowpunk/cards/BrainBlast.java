package Snowpunk.cards;

import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.BrainBlastPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import static Snowpunk.SnowpunkMod.makeID;

public class BrainBlast extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(BrainBlast.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    public BrainBlast() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = 8;
//        CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD);
        magicNumber = baseMagicNumber = 1;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        Wiz.atb(new VFXAction(player, new MindblastEffect(player.dialogX, player.dialogY, player.flipHorizontal), 0.1F));
        allDmg(AbstractGameAction.AttackEffect.NONE);
        Wiz.applyToSelf(new BrainBlastPower(Wiz.adp(), magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(3));
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }
}