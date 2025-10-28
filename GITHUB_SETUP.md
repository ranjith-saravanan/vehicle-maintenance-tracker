# GitHub Setup Guide

Follow these steps to push your project to GitHub.

## Step 1: Create GitHub Repository

1. Go to [GitHub](https://github.com) and sign in
2. Click the **+** icon in the top-right corner
3. Select **New repository**
4. Configure your repository:
   - **Repository name**: `vehicle-maintenance-tracker`
   - **Description**: "A professional Java application for tracking vehicle maintenance schedules with CLI and GUI interfaces"
   - **Visibility**: Choose Public or Private
   - **DO NOT** initialize with README, .gitignore, or license (we already have these)
5. Click **Create repository**

## Step 2: Connect Local Repository to GitHub

After creating the repository, GitHub will show you commands. Use these:

```powershell
# Add remote repository (replace YOUR-USERNAME with your GitHub username)
git remote add origin https://github.com/YOUR-USERNAME/vehicle-maintenance-tracker.git

# Verify remote was added
git remote -v

# Push your code to GitHub
git push -u origin main
```

## Step 3: Update README with Your GitHub URL

After pushing, update these files to include your actual GitHub URL:

1. **README.md** - Replace `yourusername` with your GitHub username
2. **CONTRIBUTING.md** - Update repository URLs
3. **CHANGELOG.md** - Update release links

Search and replace: `yourusername` → `YOUR-ACTUAL-USERNAME`

## Step 4: Configure Repository Settings (Optional)

On GitHub, go to your repository and configure:

### About Section
- Add description: "Professional Java maintenance tracking system with CLI and GUI"
- Add topics/tags: `java`, `swing`, `maintenance`, `scheduling`, `csv`, `maven`, `desktop-application`
- Add website (if you have one)

### Branch Protection (Recommended)
1. Go to Settings → Branches
2. Add rule for `main` branch
3. Enable:
   - Require pull request reviews before merging
   - Require status checks to pass (GitHub Actions)

### GitHub Pages (Optional)
1. Go to Settings → Pages
2. Source: Deploy from branch `main`
3. Folder: `/docs`
4. Your documentation will be available at: `https://YOUR-USERNAME.github.io/vehicle-maintenance-tracker/`

## Step 5: Add Social Preview Image (Optional)

1. Create a nice screenshot of your GUI (1280x640 recommended)
2. Go to Settings → General
3. Scroll to "Social preview"
4. Upload your image

## Step 6: Create First Release

1. Go to Releases → Create a new release
2. Click "Choose a tag"
3. Type: `v1.0.0` and click "Create new tag"
4. Release title: `Vehicle Maintenance Tracker v1.0.0`
5. Description:
   ```markdown
   ## Initial Release 🎉
   
   ### Features
   - ✅ CLI and GUI interfaces
   - ✅ Smart maintenance scheduling
   - ✅ Alert system for overdue/upcoming maintenance
   - ✅ CSV-based data storage
   - ✅ Support for 600+ records
   - ✅ Color-coded status indicators
   
   ### Installation
   See [README.md](README.md) for installation instructions.
   
   ### Documentation
   - [API Documentation](docs/API.md)
   - [Architecture Guide](docs/ARCHITECTURE.md)
   ```
6. Click "Publish release"

## Step 7: Verify Everything Works

Check that:
- [ ] Repository is visible on GitHub
- [ ] README displays correctly with badges
- [ ] All files are present
- [ ] GitHub Actions runs successfully (check Actions tab)
- [ ] Links in documentation work

## Common Issues and Solutions

### Issue: Permission Denied (Public Key)

**Solution**: Use HTTPS instead of SSH or set up SSH keys:
```powershell
# Use HTTPS URL
git remote set-url origin https://github.com/YOUR-USERNAME/vehicle-maintenance-tracker.git
```

### Issue: "fatal: Authentication failed"

**Solution**: Use Personal Access Token:
1. Go to GitHub Settings → Developer settings → Personal access tokens
2. Generate new token with `repo` scope
3. Use token as password when pushing

### Issue: Large file size warning

**Solution**: Our `.gitignore` already excludes large files. If you see warnings:
```powershell
# Check file sizes
git ls-files -z | xargs -0 du -h | sort -hr | head -20
```

## Future Workflow

### Making Changes

```powershell
# 1. Make your code changes
# Edit files...

# 2. Check status
git status

# 3. Stage changes
git add .

# 4. Commit with descriptive message
git commit -m "Add: feature description"

# 5. Push to GitHub
git push origin main
```

### Working with Branches

```powershell
# Create feature branch
git checkout -b feature/new-feature

# Make changes and commit
git add .
git commit -m "Implement new feature"

# Push feature branch
git push origin feature/new-feature

# Create Pull Request on GitHub
# After merge, delete branch and switch back
git checkout main
git pull origin main
git branch -d feature/new-feature
```

## Git Best Practices

1. **Commit Often**: Small, focused commits are better
2. **Write Good Messages**: Clear, descriptive commit messages
3. **Use Branches**: Don't commit directly to main for big changes
4. **Pull Before Push**: Always pull latest changes first
5. **Review Changes**: Use `git diff` before committing

## Example Commit Message Format

```
<type>: <short description>

<optional longer description>

<optional footer>
```

Types:
- `Add`: New feature
- `Fix`: Bug fix
- `Update`: Modify existing feature
- `Docs`: Documentation changes
- `Style`: Code formatting
- `Refactor`: Code restructuring
- `Test`: Adding tests
- `Chore`: Maintenance tasks

Example:
```
Add: email notification system

Implemented email alerts for overdue maintenance using JavaMail API.
Users can configure email settings in preferences dialog.

Closes #15
```

---

## Ready to Push?

Run these commands (replace YOUR-USERNAME):

```powershell
# Add remote
git remote add origin https://github.com/YOUR-USERNAME/vehicle-maintenance-tracker.git

# Push to GitHub
git push -u origin main
```

🎉 Your project is now on GitHub at a professional level!

---

**Next Steps**: 
- Add screenshots to `docs/images/`
- Star your own repository
- Share with the community
- Wait for contributions!
