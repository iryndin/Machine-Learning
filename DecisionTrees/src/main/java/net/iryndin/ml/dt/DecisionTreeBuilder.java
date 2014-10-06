package net.iryndin.ml.dt;

/**
 * Builder builds DecisionTree according to an algorithm (CART, ID3, C4.5, C5.0)
 */
public interface DecisionTreeBuilder {
    DecisionTreeClassifier buildTree(DataSet dataset);
}
