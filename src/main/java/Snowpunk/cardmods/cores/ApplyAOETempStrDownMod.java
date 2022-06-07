package Snowpunk.cardmods.cores;

import Snowpunk.actions.ApplyPowerActionWithFollowup;
import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class ApplyAOETempStrDownMod extends AbstractCoreCardMod {
    int effect;
    int upEffect;

    public ApplyAOETempStrDownMod(String name, String description, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target, int effect, int upEffect) {
        super(name, description, type, rarity, target);
        this.effect = effect;
        this.upEffect = upEffect;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        collateCardBasics(card);
        collateMagic(card, effect, upEffect);
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addUseConsumer((p, m) -> {
                for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                    if (!mo.isDeadOrEscaped()) {
                        if (useSecondVar) {
                            Wiz.atb(new ApplyPowerActionWithFollowup(new ApplyPowerAction(mo, p, new StrengthPower(mo, -((AssembledCard) card).secondMagic)), new ApplyPowerAction(mo, p, new GainStrengthPower(mo, ((AssembledCard) card).secondMagic))));
                        } else {
                            Wiz.atb(new ApplyPowerActionWithFollowup(new ApplyPowerAction(mo, p, new StrengthPower(mo, -card.magicNumber)), new ApplyPowerAction(mo, p, new GainStrengthPower(mo, card.magicNumber))));
                        }
                    }
                }
            });
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ApplyAOETempStrDownMod(name, description, type, rarity, target, effect, upEffect);
    }
}
