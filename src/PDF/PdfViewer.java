/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PDF;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.common.base.CharMatcher;
import static com.google.common.base.Strings.isNullOrEmpty;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PagePanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
 
public class PdfViewer extends JPanel {
    private static enum Navigation {GO_FIRST_PAGE, FORWARD, BACKWARD, GO_LAST_PAGE, GO_N_PAGE}
 
    private static final CharMatcher POSITIVE_DIGITAL = CharMatcher.anyOf("0123456789");
    private static final String GO_PAGE_TEMPLATE = "%s of %s";
    private static final int FIRST_PAGE = 1;
    private int currentPage = FIRST_PAGE;
    private JButton btnFirstPage;
    private JButton btnPreviousPage;
    private JTextField txtGoPage;
    private JButton btnNextPage;
    private JButton btnLastPage;
    private PagePanel pagePanel;
    private PDFFile pdfFile;
 
    public PdfViewer() {
        initial();
    }
 
    private void initial() {
        setLayout(new BorderLayout(0, 0));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add(topPanel, BorderLayout.NORTH);
        btnFirstPage = createButton("|<<");
        topPanel.add(btnFirstPage);
        btnPreviousPage = createButton("<<");
        topPanel.add(btnPreviousPage);
        txtGoPage = new JTextField(10);
        txtGoPage.setHorizontalAlignment(JTextField.CENTER);
        topPanel.add(txtGoPage);
        btnNextPage = createButton(">>");
        topPanel.add(btnNextPage);
        btnLastPage = createButton(">>|");
        topPanel.add(btnLastPage);
        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);
        JPanel viewPanel = new JPanel(new BorderLayout(0, 0));
        scrollPane.setViewportView(viewPanel);
 
        pagePanel = new PagePanel();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        pagePanel.setPreferredSize(screenSize);
        viewPanel.add(pagePanel, BorderLayout.CENTER);
 
        disableAllNavigationButton();
 
        btnFirstPage.addActionListener(new PdfViewer.PageNavigationListener(PdfViewer.Navigation.GO_FIRST_PAGE));
        btnPreviousPage.addActionListener(new PdfViewer.PageNavigationListener(PdfViewer.Navigation.BACKWARD));
        btnNextPage.addActionListener(new PdfViewer.PageNavigationListener(PdfViewer.Navigation.FORWARD));
        btnLastPage.addActionListener(new PdfViewer.PageNavigationListener(PdfViewer.Navigation.GO_LAST_PAGE));
        txtGoPage.addActionListener(new PdfViewer.PageNavigationListener(PdfViewer.Navigation.GO_N_PAGE));
    }
 
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(55, 20));
 
        return button;
    }
 
    private void disableAllNavigationButton() {
        btnFirstPage.setEnabled(false);
        btnPreviousPage.setEnabled(false);
        btnNextPage.setEnabled(false);
        btnLastPage.setEnabled(false);
    }
 
    private boolean isMoreThanOnePage(PDFFile pdfFile) {
        return pdfFile.getNumPages() > 1;
    }
 
    private class PageNavigationListener implements ActionListener {
        private final PdfViewer.Navigation navigation;
 
        private PageNavigationListener(PdfViewer.Navigation navigation) {
            this.navigation = navigation;
        }
 
        public void actionPerformed(ActionEvent e) {
            if (pdfFile == null) {
                return;
            }
 
            int numPages = pdfFile.getNumPages();
            if (numPages <= 1) {
                disableAllNavigationButton();
            } else {
                if (navigation == PdfViewer.Navigation.FORWARD && hasNextPage(numPages)) {
                    goPage(currentPage, numPages);
                }
 
                if (navigation == PdfViewer.Navigation.GO_LAST_PAGE) {
                    goPage(numPages, numPages);
                }
 
                if (navigation == PdfViewer.Navigation.BACKWARD && hasPreviousPage()) {
                    goPage(currentPage, numPages);
                }
 
                if (navigation == PdfViewer.Navigation.GO_FIRST_PAGE) {
                    goPage(FIRST_PAGE, numPages);
                }
 
                if (navigation == PdfViewer.Navigation.GO_N_PAGE) {
                    String text = txtGoPage.getText();
                    boolean isValid = false;
                    if (!isNullOrEmpty(text)) {
                        boolean isNumber = POSITIVE_DIGITAL.matchesAllOf(text);
                        if (isNumber) {
                            int pageNumber = Integer.valueOf(text);
                            if (pageNumber >= 1 && pageNumber <= numPages) {
                                goPage(Integer.valueOf(text), numPages);
                                isValid = true;
                            }
                        }
                    }
                    if (!isValid) {
                        JOptionPane.showMessageDialog(PdfViewer.this, format("Invalid page number '%s' in this document", text));
                        txtGoPage.setText(format(GO_PAGE_TEMPLATE, currentPage, numPages));
                    }
                }
            }
        }
       public  String format(String template, Object... args) {
        template = String.valueOf(template); // null -> "null"
        // start substituting the arguments into the '%s' placeholders
        StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template.substring(templateStart));
 
        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }
 
        return builder.toString();
    }
 

//Read more: http://seanshou.blogspot.com/2012/09/java-better-performance-string-formatter.html#ixzz359IiEaCo

 
        private void goPage(int pageNumber, int numPages) {
            currentPage = pageNumber;
            PDFPage page = pdfFile.getPage(currentPage);
            pagePanel.showPage(page);
            boolean notFirstPage = isNotFirstPage();
            btnFirstPage.setEnabled(notFirstPage);
            btnPreviousPage.setEnabled(notFirstPage);
            txtGoPage.setText(format(GO_PAGE_TEMPLATE, currentPage, numPages));
            boolean notLastPage = isNotLastPage(numPages);
            btnNextPage.setEnabled(notLastPage);
            btnLastPage.setEnabled(notLastPage);
        }
 
        private boolean hasNextPage(int numPages) {
            return (++currentPage) <= numPages;
        }
 
        private boolean hasPreviousPage() {
            return (--currentPage) >= FIRST_PAGE;
        }
 
        private boolean isNotLastPage(int numPages) {
            return currentPage != numPages;
        }
 
        private boolean isNotFirstPage() {
            return currentPage != FIRST_PAGE;
        }
    }
 
    public PagePanel getPagePanel() {
        return pagePanel;
    }
 
    public void setPDFFile(PDFFile pdfFile) {
        this.pdfFile = pdfFile;
        currentPage = FIRST_PAGE;
        disableAllNavigationButton();
        //txtGoPage.setText(format(GO_PAGE_TEMPLATE , pdfFile.getNumPages()));
        boolean moreThanOnePage = isMoreThanOnePage(pdfFile);
        btnNextPage.setEnabled(moreThanOnePage);
        btnLastPage.setEnabled(moreThanOnePage);
    }
    /*
    public static void main(String[] args) {
        try {
            long heapSize = Runtime.getRuntime().totalMemory();
            System.out.println("Heap Size = " + heapSize);
 
            JFrame frame = new JFrame("PDF Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
            //load a pdf from a byte buffer
            File file = new File("D:\\Data ku\\Mikrotik_Advanced.pdf");
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            FileChannel channel = raf.getChannel();
            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            final PDFFile pdffile = new PDFFile(buf);
            PdfViewer pdfViewer = new PdfViewer();
            pdfViewer.setPDFFile(pdffile);
            
            frame.add(pdfViewer);
            frame.pack();
            frame.setVisible(true);
 
            PDFPage page = pdffile.getPage(0);
            pdfViewer.getPagePanel().showPage(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    * */

//Read more: http://seanshou.blogspot.com/2012/10/java-swing-pdf-viewer.html#ixzz359GetTjV

}



//Read more: http://seanshou.blogspot.com/2012/10/java-swing-pdf-viewer.html#ixzz3597Ucyx1
