package com.lamerman;

import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import com.generic.readandbill.R;

public class FileDialog extends ListActivity {
    public static final String CAN_SELECT_DIR = "CAN_SELECT_DIR";
    public static final String FORMAT_FILTER = "FORMAT_FILTER";
    private static final String ITEM_IMAGE = "image";
    private static final String ITEM_KEY = "key";
    public static final String RESULT_PATH = "RESULT_PATH";
    private static final String ROOT = "/";
    public static final String SELECTION_MODE = "SELECTION_MODE";
    public static final String START_PATH = "START_PATH";
    private boolean canSelectDir;
    private String currentPath;
    private String[] formatFilter;
    private InputMethodManager inputManager;
    private HashMap<String, Integer> lastPositions;
    private LinearLayout layoutCreate;
    private LinearLayout layoutSelect;
    private EditText mFileName;
    private ArrayList<HashMap<String, Object>> mList;
    private TextView myPath;
    private String parentPath;
    private List<String> path;
    private Button selectButton;
    private File selectedFile;
    private int selectionMode;

    /* renamed from: com.lamerman.FileDialog.1 */
    class C00241 implements OnClickListener {
        C00241() {
        }

        public void onClick(View v) {
            if (FileDialog.this.selectedFile != null) {
                FileDialog.this.getIntent().putExtra(FileDialog.RESULT_PATH, FileDialog.this.selectedFile.getPath());
                FileDialog.this.setResult(-1, FileDialog.this.getIntent());
                FileDialog.this.finish();
            }
        }
    }

    /* renamed from: com.lamerman.FileDialog.2 */
    class C00252 implements OnClickListener {
        C00252() {
        }

        public void onClick(View v) {
            FileDialog.this.setCreateVisible(v);
            FileDialog.this.mFileName.setText("");
            FileDialog.this.mFileName.requestFocus();
        }
    }

    /* renamed from: com.lamerman.FileDialog.3 */
    class C00263 implements OnClickListener {
        C00263() {
        }

        public void onClick(View v) {
            FileDialog.this.setSelectVisible(v);
        }
    }

    /* renamed from: com.lamerman.FileDialog.4 */
    class C00274 implements OnClickListener {
        C00274() {
        }

        public void onClick(View v) {
            if (FileDialog.this.mFileName.getText().length() > 0) {
                FileDialog.this.getIntent().putExtra(FileDialog.RESULT_PATH, FileDialog.this.currentPath + FileDialog.ROOT + FileDialog.this.mFileName.getText());
                FileDialog.this.setResult(-1, FileDialog.this.getIntent());
                FileDialog.this.finish();
            }
        }
    }

    /* renamed from: com.lamerman.FileDialog.5 */
    class C00285 implements DialogInterface.OnClickListener {
        C00285() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    public FileDialog() {
        this.path = null;
        this.currentPath = ROOT;
        this.selectionMode = 0;
        this.formatFilter = null;
        this.canSelectDir = false;
        this.lastPositions = new HashMap();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(0, getIntent());
        setContentView(R.layout.file_dialog_main);
        this.myPath = (TextView) findViewById(R.id.path);
        this.mFileName = (EditText) findViewById(R.id.fdEditTextFile);
        this.inputManager = (InputMethodManager) getSystemService("input_method");
        this.selectButton = (Button) findViewById(R.id.fdButtonSelect);
        this.selectButton.setEnabled(false);
        this.selectButton.setOnClickListener(new C00241());
        Button newButton = (Button) findViewById(R.id.fdButtonNew);
        newButton.setOnClickListener(new C00252());
        this.selectionMode = getIntent().getIntExtra(SELECTION_MODE, 0);
        this.formatFilter = getIntent().getStringArrayExtra(FORMAT_FILTER);
        this.canSelectDir = getIntent().getBooleanExtra(CAN_SELECT_DIR, false);
        if (this.selectionMode == 1) {
            newButton.setEnabled(false);
        }
        this.layoutSelect = (LinearLayout) findViewById(R.id.fdLinearLayoutSelect);
        this.layoutCreate = (LinearLayout) findViewById(R.id.fdLinearLayoutCreate);
        this.layoutCreate.setVisibility(8);
        ((Button) findViewById(R.id.fdButtonCancel)).setOnClickListener(new C00263());
        ((Button) findViewById(R.id.fdButtonCreate)).setOnClickListener(new C00274());
        String startPath = getIntent().getStringExtra(START_PATH);
        if (startPath == null) {
            startPath = ROOT;
        }
        if (this.canSelectDir) {
            this.selectedFile = new File(startPath);
            this.selectButton.setEnabled(true);
        }
        getDir(startPath);
    }

    private void getDir(String dirPath) {
        boolean useAutoSelection = dirPath.length() < this.currentPath.length();
        Integer position = (Integer) this.lastPositions.get(this.parentPath);
        getDirImpl(dirPath);
        if (position != null && useAutoSelection) {
            getListView().setSelection(position.intValue());
        }
    }

    private void getDirImpl(String dirPath) {
        this.currentPath = dirPath;
        List<String> item = new ArrayList();
        this.path = new ArrayList();
        this.mList = new ArrayList();
        File f = new File(this.currentPath);
        File[] files = f.listFiles();
        if (files == null) {
            this.currentPath = ROOT;
            f = new File(this.currentPath);
            files = f.listFiles();
        }
        this.myPath.setText(getText(R.string.location) + ": " + this.currentPath);
        if (!this.currentPath.equals(ROOT)) {
            item.add(ROOT);
            addItem(ROOT, R.drawable.folder);
            this.path.add(ROOT);
            item.add("../");
            addItem("../", R.drawable.folder);
            this.path.add(f.getParent());
            this.parentPath = f.getParent();
        }
        TreeMap<String, String> dirsMap = new TreeMap();
        TreeMap<String, String> dirsPathMap = new TreeMap();
        TreeMap<String, String> filesMap = new TreeMap();
        TreeMap<String, String> filesPathMap = new TreeMap();
        for (File file : files) {
            if (file.isDirectory()) {
                String dirName = file.getName();
                dirsMap.put(dirName, dirName);
                dirsPathMap.put(dirName, file.getPath());
            } else {
                String fileName = file.getName();
                String fileNameLwr = fileName.toLowerCase();
                if (this.formatFilter != null) {
                    boolean contains = false;
                    for (String toLowerCase : this.formatFilter) {
                        if (fileNameLwr.endsWith(toLowerCase.toLowerCase())) {
                            contains = true;
                            break;
                        }
                    }
                    if (contains) {
                        filesMap.put(fileName, fileName);
                        filesPathMap.put(fileName, file.getPath());
                    }
                } else {
                    filesMap.put(fileName, fileName);
                    filesPathMap.put(fileName, file.getPath());
                }
            }
        }
        item.addAll(dirsMap.tailMap("").values());
        item.addAll(filesMap.tailMap("").values());
        this.path.addAll(dirsPathMap.tailMap("").values());
        this.path.addAll(filesPathMap.tailMap("").values());
        SimpleAdapter fileList = new SimpleAdapter(this, this.mList, R.layout.file_dialog_row, new String[]{ITEM_KEY, ITEM_IMAGE}, new int[]{R.id.fdrowtext, R.id.fdrowimage});
        for (String dir : dirsMap.tailMap("").values()) {
            addItem(dir, R.drawable.folder);
        }
        for (String addItem : filesMap.tailMap("").values()) {
            addItem(addItem, R.drawable.file);
        }
        fileList.notifyDataSetChanged();
        setListAdapter(fileList);
    }

    private void addItem(String fileName, int imageId) {
        HashMap<String, Object> item = new HashMap();
        item.put(ITEM_KEY, fileName);
        item.put(ITEM_IMAGE, Integer.valueOf(imageId));
        this.mList.add(item);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        File file = new File((String) this.path.get(position));
        setSelectVisible(v);
        if (file.isDirectory()) {
            this.selectButton.setEnabled(false);
            if (file.canRead()) {
                this.lastPositions.put(this.currentPath, Integer.valueOf(position));
                getDir((String) this.path.get(position));
                if (this.canSelectDir) {
                    this.selectedFile = file;
                    v.setSelected(true);
                    this.selectButton.setEnabled(true);
                    return;
                }
                return;
            }
            new Builder(this).setIcon(R.drawable.icon).setTitle("[" + file.getName() + "] " + getText(R.string.cant_read_folder)).setPositiveButton("OK", new C00285()).show();
            return;
        }
        this.selectedFile = file;
        v.setSelected(true);
        this.selectButton.setEnabled(true);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        this.selectButton.setEnabled(false);
        if (this.layoutCreate.getVisibility() == 0) {
            this.layoutCreate.setVisibility(8);
            this.layoutSelect.setVisibility(0);
        } else if (this.currentPath.equals(ROOT)) {
            return super.onKeyDown(keyCode, event);
        } else {
            getDir(this.parentPath);
        }
        return true;
    }

    private void setCreateVisible(View v) {
        this.layoutCreate.setVisibility(0);
        this.layoutSelect.setVisibility(8);
        this.inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        this.selectButton.setEnabled(false);
    }

    private void setSelectVisible(View v) {
        this.layoutCreate.setVisibility(8);
        this.layoutSelect.setVisibility(0);
        this.inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        this.selectButton.setEnabled(false);
    }
}
