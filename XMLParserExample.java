import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParserExample extends JFrame {
    private JTree tree;

    public XMLParserExample() {
        setTitle("XML Parser Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Создание корневого узла дерева
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("XML");

        // Создание модели дерева
        DefaultTreeModel treeModel = new DefaultTreeModel(root);

        // Создание компонента JTree с моделью дерева
        tree = new JTree(treeModel);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        // Создание панели прокрутки для компонента JTree
        JScrollPane scrollPane = new JScrollPane(tree);

        // Добавление панели прокрутки на фрейм
        getContentPane().add(scrollPane);

        // Парсинг XML-файла и добавление его содержимого в дерево
        parseXML(root, "path/to/xml/file.xml");
    }

    private void parseXML(DefaultMutableTreeNode parent, String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);

            Element rootElement = document.getDocumentElement();
            NodeList nodeList = rootElement.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Создание узла для каждого элемента XML-файла
                    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(element.getTagName());
                    parent.add(childNode);

                    // Рекурсивный вызов для обработки вложенных элементов
                    parseXML(childNode, filePath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            XMLParserExample example = new XMLParserExample();
            example.setVisible(true);
        });
    }
}
