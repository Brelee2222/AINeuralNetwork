public class CatlystNeuralNet extends NeuralNetwork {
    // Catlyst is my signature
    // This Network is more experimental for different sizes, amounts of inputs, and layers and learning rate
    // It allows the network to do several operations in sequence effectively allowing interactions between inputs

    // Layers : Ability to think/perform neuron interactions
    // LayerSize : Capacity to think
    // Inputs : how many sensors
    // Results : terminals

    public CatlystNeuralNet(int inputs, int results, int layers, int layerSize) {
        neuronLayers = new Neuron[layers][];
        Neuron[] initialNeurons = neuronLayers[layers-1] = new Neuron[layerSize];

    }
}
